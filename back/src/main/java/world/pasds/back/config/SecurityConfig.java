package world.pasds.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/app/api/member/signup"
            , "/app/api/member/test"
    };

// Authentication: 인증: 사용자의 신원을 확인
// Authorization: 인가: 사용자가 특정 작업을 수행할 권한이 있는지

// csrf: CSRF 토큰을 요구
// authorizeHttpRequests: 접근 제어
// httpBasic: 기본 인증 헤더에 사용자 이름과 비밀번호를 인코딩하여 포함
// formLogin: 기본 로그인 페이지를 제공

// 설정 순서가 실제 행동 순서가 아니다

// 기본설정들
// 세션 관리
// CSRF 보호 활성화
// 기본 로그인 및 로그아웃 페이지
// HTTP 기본 인증
// 접근 제어
// 정적 리소스의 보안 무시
// 인증 메커니즘 구성

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
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
                    authorizeHttpRequests.requestMatchers(AUTH_WHITELIST).permitAll();
                    authorizeHttpRequests.anyRequest().authenticated();
                })
                .cors((cors) -> {
                    cors.configurationSource(corsConfigurationSource());
                })

                // TODO: JWT 필터
                // TODO: JWT 필터하고 나서 권한 부여 필터?????

                .build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        int strength = 12; // 비밀번호 해싱 강도 설정
        return new BCryptPasswordEncoder(strength);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173"
//                "/**"
        )); // 허용할 오리진

        configuration.setAllowedMethods(List.of("GET", "POST")); // 허용할 HTTP 메서드

        configuration.setAllowCredentials(true);
        // 이 설정은 브라우저에게 인증 정보(예: 쿠키, HTTP 인증 및 클라이언트 SSL 인증서)와
        // 함께 요청을 보내도록 허용할지 여부를 지정합니다.
        // true로 설정하면, 인증 정보를 포함한 요청이 가능합니다.

        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        // CORS 설정을 URL 패턴에 매핑
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // 모든 경로에 대해 위에서 정의된 configuration 설정을 적용하겠다
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}