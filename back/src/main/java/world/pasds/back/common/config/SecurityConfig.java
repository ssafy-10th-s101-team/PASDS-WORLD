package world.pasds.back.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import world.pasds.back.common.filter.FirstLoginAuthenticationFilter;
//import world.pasds.back.common.filter.SecondLoginAuthenticationFilter;
import world.pasds.back.common.util.CustomUserDetailsService;
import world.pasds.back.common.util.JwtTokenProvider;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] AUTHENTICATED_ENDPOINTS = {
            "app/api/member/first-login",
            "app/api/member/second-login"
    };
    private static final String[] PUBLIC_ENDPOINTS = {
            "/**",
    };

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder createBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager configureAuthenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(createBCryptPasswordEncoder());
        return auth.build();
    }

    @Bean
    public SecurityFilterChain configureSecurityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        return http
                .sessionManagement((sessionManagement) -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
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
                    authorizeHttpRequests.requestMatchers(AUTHENTICATED_ENDPOINTS).authenticated();
                    authorizeHttpRequests.requestMatchers(PUBLIC_ENDPOINTS).permitAll();
                })
                .cors((cors) -> {
                    cors.configurationSource(createCorsConfigurationSource());
                })
                .addFilterBefore(new FirstLoginAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new SecondLoginAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource createCorsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
