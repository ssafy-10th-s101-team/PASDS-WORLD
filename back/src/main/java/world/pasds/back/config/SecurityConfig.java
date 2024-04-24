package world.pasds.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        int strength = 12; // 비밀번호 해싱 강도 설정
        return new BCryptPasswordEncoder(strength);
    }

    // Authentication: 인증: 사용자의 신원을 확인
    // Authorization: 인가: 사용자가 특정 작업을 수행할 권한이 있는지
    // Permission 권한: 이 모든 과정을 상세하게 정의

    // csrf: CSRF 토큰을 요구
    // authorizeHttpRequests: 접근 제어 anyRequest().authenticated(): 모든 요청에 대해 인증된 사용자만 접근
    // httpBasic: 기본 인증 헤더에 사용자 이름과 비밀번호를 인코딩하여 포함
    // formLogin: 기본 로그인 페이지를 제공

    // 설정 순서가 실제 행동 순서가 아니다

    // Customizer.withDefaults()는 Spring Security가 제공하는 기본적인 방어 메커니즘 사용


    private static final String[] AUTH_WHITELIST = {
            "/app/api/member/signup",
            "/app/api/member/test"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> {
                    csrf.disable();
                })
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(AUTH_WHITELIST).permitAll();
                    authorize.anyRequest().authenticated();
                });


        // TODO: JWT 필터

        // TODO: JWT 필터하고 나서 권한 부여 필터?????

        return http.build();
    }
}
