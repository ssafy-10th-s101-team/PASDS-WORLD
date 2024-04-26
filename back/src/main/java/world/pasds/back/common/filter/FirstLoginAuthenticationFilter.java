package world.pasds.back.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import world.pasds.back.common.util.JwtTokenProvider;

import java.io.IOException;
import java.util.Map;

@Component
public class FirstLoginAuthenticationFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AntPathRequestMatcher firstLoginRequestMatcher = new AntPathRequestMatcher("/app/api/member/first-login", "POST");

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${security.pepper}")
    private String pepper;

    private final AuthenticationManager authenticationManager;

    public FirstLoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!firstLoginRequestMatcher.matches(request))
            filterChain.doFilter(request, response);

        try {
            Map<String, String> loginRequestData = objectMapper.readValue(request.getInputStream(), Map.class);
            String email = loginRequestData.get("email");
            String password = loginRequestData.get("password") + pepper;

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
            authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            Authentication authentication = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 발급받은 임시 토큰을 쿠키에 추가
            String temporaryToken = jwtTokenProvider.generateTemporaryJwtToken(authentication);
            Cookie tempTokenCookie = new Cookie("TemporaryToken", temporaryToken);
            tempTokenCookie.setHttpOnly(true);
            tempTokenCookie.setSecure(true);
            tempTokenCookie.setPath("/");
            response.addCookie(tempTokenCookie);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Temporary token issued and set in cookie.");
            response.getWriter().flush();
            response.getWriter().close();
            return; // 요청 처리를 종료하여 컨트롤러로 넘어가지 않음

        } catch (AuthenticationException e) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return; // 예외 발생 시 다음 필터로 이동하지 않음
        }


    }
}