//package world.pasds.back.common.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.web.filter.OncePerRequestFilter;
//import world.pasds.back.common.util.JwtTokenProvider;
//
//import java.io.IOException;
//import java.util.Map;
//
//public class SecondLoginAuthenticationFilter extends OncePerRequestFilter {
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final RequestMatcher requestMatcher = new AntPathRequestMatcher("/app/api/member/second-login", "POST");
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenProvider jwtTokenProvider;
//
//    public SecondLoginAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
//        this.authenticationManager = authenticationManager;
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (requestMatcher.matches(request)) {
//            try {
//
//                // temporaryToken 검사하기
//                String header = request.getHeader("Authorization");
//                if (header == null) {
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//                    return;
//                }
//
//                String token = header.substring(7);
//                if (!jwtTokenProvider.validateToken(token)) {
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
//                    return;
//                }
//
//                Authentication authentication = jwtTokenProvider.getAuthentication(token);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                // totpCode 검사하기
//                Map<String, String> requestData = objectMapper.readValue(request.getInputStream(), Map.class);
//                String totpCode = requestData.get("totpCode");
//
//                // TODO: 진짜 TOTP VALIDATE 로직으로 변경하기
//                if (!totpCode.equals("123456")) {
//                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//                    return;
//                }
//
//                // atk, rtk 만들어서 돌려주기
//                String accessToken = jwtTokenProvider.generateAccessJwtToken(authentication);
//                String refreshToken = jwtTokenProvider.generateRefreshJwtToken(authentication);
//
//                response.setHeader("Access-Token", "Bearer " + accessToken);
//                response.setHeader("Refresh-Token", "Bearer " + refreshToken);
//                response.setStatus(HttpServletResponse.SC_OK);
//                response.getWriter().write("Login successful with TOTP.");
//                response.getWriter().flush();
//                response.getWriter().close();
//                return; // 필터 체인 종료
//
//                return; // 요청 처리를 종료하여 컨트롤러로 넘어가지 않음
//
//            } catch (AuthenticationException e) {
//                SecurityContextHolder.clearContext();
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
//                return; // 예외 발생 시 다음 필터로 이동하지 않음
//            }
//        }
//        // 이 줄은 인증이 필요하지 않은 다른 요청에 대해 필터 체인을 계속 실행합니다.
//        filterChain.doFilter(request, response);
//    }
//
//}
