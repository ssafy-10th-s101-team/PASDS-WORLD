package world.pasds.back.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import world.pasds.back.common.filter.CustomAuthenticationFilter;
import world.pasds.back.common.service.KeyService;
import world.pasds.back.common.util.CookieProvider;
import world.pasds.back.common.util.JwtTokenProvider;
import world.pasds.back.member.service.CustomUserDetailsService;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 토큰을 진짜 하나도 안들고 오는 애들!
    private static final String[] PUBLIC_ENDPOINTS = {
            // 회원가입 이메일 인증코드 받기
            "/app/api/email/signup-verification-requests",
            // 비밀번소 재설정 이메일 인증코드 받기
            "/app/api/email/password-verification-requests",
            // 이메일 인증하기
            "/app/api/email/verification-email-code",
            "/app/api/key-rotate/handle-masterkey-change",
            "/actuator/**"
    };

    @Value("${security.pepper}")
    private String passwordPepper;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CookieProvider cookieProvider;

    @Autowired
    private KeyService keyService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager buildAuthenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(bcryptPasswordEncoder());
        return auth.build();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new CustomAuthenticationFilter(authenticationManager, getRequestMatchers(), jwtTokenProvider, cookieProvider, passwordPepper, keyService, redisTemplate);
    }

    @Bean
    public SecurityFilterChain configureSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement((sessionManagement) -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests) -> {
                    authorizeHttpRequests.requestMatchers(PUBLIC_ENDPOINTS).permitAll();
                    authorizeHttpRequests.anyRequest().authenticated();
                })
                .cors((cors) -> {
                    cors.configurationSource(corsConfigurationSource());
                })
                .addFilterBefore(customAuthenticationFilter(buildAuthenticationManager(http)),
                        UsernamePasswordAuthenticationFilter.class)
                .headers((headers) -> {
                    headers.addHeaderWriter(new StaticHeadersWriter("Content-Security-Policy", "default-src 'self'; script-src 'self'; object-src 'none'; frame-ancestors 'none';"));
                    headers.addHeaderWriter(new StaticHeadersWriter("X-Frame-Options", "DENY"));
                    headers.addHeaderWriter(new StaticHeadersWriter("Strict-Transport-Security", "max-age=31536000; includeSubDomains"));
                    headers.addHeaderWriter(new StaticHeadersWriter("X-Content-Type-Options", "nosniff"));
                    headers.addHeaderWriter(new StaticHeadersWriter("Cache-Control", "no-cache, no-store, must-revalidate"));
                    headers.addHeaderWriter(new StaticHeadersWriter("Pragma", "no-cache"));
                    headers.addHeaderWriter(new StaticHeadersWriter("Expires", "0"));
                    headers.addHeaderWriter(new StaticHeadersWriter("Server", ""));
                })
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "https://localhost:5173",
                "https://www.pasds.world",
                "https://pasds.world",
                "http://localhost:8081",
                "http://3.39.81.251:8081",
                "http://10.0.1.144:8081"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    private AntPathRequestMatcher[] getRequestMatchers() {
        return Arrays.stream(PUBLIC_ENDPOINTS)
                .map(AntPathRequestMatcher::new)
                .toArray(AntPathRequestMatcher[]::new);
    }
}
