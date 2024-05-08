package world.pasds.back.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
            "/app/api/totp/email-verification-requests",
            "/app/api/totp/verification-email-code",
            "/app/api/key-rotate/handle-masterkey-change",
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
        return new CustomAuthenticationFilter(authenticationManager, getRequestMatchers(), jwtTokenProvider, cookieProvider, passwordPepper, keyService);
    }

    @Bean
    public SecurityFilterChain configureSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement((sessionManagement) -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // TODO: 실제서비스에서 CSRF 어떻게 해야하지?
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests) -> {
                    authorizeHttpRequests.requestMatchers(PUBLIC_ENDPOINTS).permitAll();
                    authorizeHttpRequests.anyRequest().authenticated();
                })
                .cors((cors) -> {
                    cors.configurationSource(corsConfigurationSource());
                })
                .addFilterBefore(customAuthenticationFilter(buildAuthenticationManager(http)),
                        UsernamePasswordAuthenticationFilter.class)
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
