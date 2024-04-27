package world.pasds.back.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import world.pasds.back.common.filter.CustomAuthenticationFilter;
import world.pasds.back.common.util.CookieProvider;
import world.pasds.back.common.util.JwtTokenProvider;
import world.pasds.back.member.service.CustomUserDetailsService;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] PUBLIC_ENDPOINTS = {
            "/app/api/member/test",
            "/app/api/member/signup",
//            "/**"
    };

    private AntPathRequestMatcher[] getRequestMatchers() {
        return Arrays.stream(PUBLIC_ENDPOINTS)
                .map(AntPathRequestMatcher::new)
                .toArray(AntPathRequestMatcher[]::new);
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

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

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CookieProvider cookieProvider;

    @Value("${security.pepper}")
    private String passwordPepper;

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new CustomAuthenticationFilter(authenticationManager, getRequestMatchers(), jwtTokenProvider, cookieProvider, passwordPepper);
    }

    @Bean
    public SecurityFilterChain configureSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement((sessionManagement) -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // TODO: 실제서비스에서 CSRF 어떻게 해야하지?
                .csrf((csrf) -> {
                    csrf.disable();
                })
                .formLogin((formLogin) -> {
                    formLogin.disable();
                })
                .logout((logout) -> {
                    logout.disable();
                })
                .httpBasic((httpBasic) -> {
                    httpBasic.disable();
                })
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

        // TODO: 실제 서비스 도메인 추가 ? kms도 추가?
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "https://your-production-site.com"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
