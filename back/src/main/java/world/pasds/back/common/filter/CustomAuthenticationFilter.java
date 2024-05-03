package world.pasds.back.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import world.pasds.back.common.dto.FirstLoginResponseDto;
import world.pasds.back.common.exception.BusinessException;
import world.pasds.back.common.exception.ExceptionCode;
import world.pasds.back.common.util.CookieProvider;
import world.pasds.back.common.util.JwtTokenProvider;
import world.pasds.back.member.entity.CustomUserDetails;
import world.pasds.back.totp.service.TotpService;

import java.io.IOException;
import java.util.Map;

import static world.pasds.back.common.exception.ExceptionCode.*;

public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private static final String TEMPORARY_TOKEN = "TEMPORARY";
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String REFRESH_TOKEN = "REFRESH";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationManager authenticationManager;
    private final AntPathRequestMatcher[] requestMatchers;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieProvider cookieProvider;
    private final String passwordPepper;
    private final TotpService totpService;

    public CustomAuthenticationFilter(
            AuthenticationManager authenticationManager,
            AntPathRequestMatcher[] requestMatchers,
            JwtTokenProvider jwtTokenProvider,
            CookieProvider cookieProvider,
            String passwordPepper,
            TotpService totpService) {
        this.authenticationManager = authenticationManager;
        this.requestMatchers = requestMatchers;
        this.jwtTokenProvider = jwtTokenProvider;
        this.cookieProvider = cookieProvider;
        this.passwordPepper = passwordPepper;
        this.totpService = totpService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (shouldSkipFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        processBasedOnPath(request, response, filterChain);
    }

    private boolean shouldSkipFilter(HttpServletRequest request) {
        for (AntPathRequestMatcher matcher : requestMatchers) {
            if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    }

    private void processBasedOnPath(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String path = request.getRequestURI();

        switch (path) {
            case "/app/api/member/first-login":
                handleFirstLogin(request, response);
                break;
            case "/app/api/member/second-login":
                handleSecondLogin(request, response);
                break;
            case "/app/api/member/logout":
                handleLogout(response);
                break;
            default:
                handleDefaultCase(request, response, filterChain);
                break;
        }
    }

    private void handleFirstLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // email, password 검사
        Map<String, String> requestBody = objectMapper.readValue(request.getInputStream(), Map.class);
        String email = requestBody.get("email");
        String pepperedPassword = requestBody.get("password") + passwordPepper;

        Authentication authentication;

        try {
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, pepperedPassword);
            authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            authentication = authenticationManager.authenticate(authRequest);
        } catch (Exception e) {
            respondCaseFail(response, FIRST_LOGIN_AUTHENTICAT_FAIL);
            return;
        }

        // 성공 
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String temporaryJwtToken = jwtTokenProvider.generateTemporaryToken(customUserDetails.getMemberId());
        cookieProvider.addCookie(response, TEMPORARY_TOKEN, temporaryJwtToken);

        FirstLoginResponseDto firstLoginResponseDto = FirstLoginResponseDto
                .builder()
                .nickname(customUserDetails.getNickname())
                .build();

        respondCaseSuccess(response, firstLoginResponseDto);
    }

    private void handleSecondLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // temporaryToken 검사
        String temporaryToken = cookieProvider.getCookieValue(request, TEMPORARY_TOKEN);
        Authentication authentication = jwtTokenProvider.getAuthentication(temporaryToken);
        if (authentication == null) {
            cookieProvider.removeCookie(response,TEMPORARY_TOKEN);
            respondCaseFail(response, TEMPORARY_TOKEN_EXPIRED);
            return;
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // totp 검사
        Map<String, String> requestBody = objectMapper.readValue(request.getInputStream(), Map.class);
        String totpCode = requestBody.get("totpCode");
        if (!isValidTotp(userDetails.getMemberId(), totpCode)) {
            respondCaseFail(response,TOTP_CODE_NOT_SAME);
            return;
        }

        // 성공
        // TODO: Redis 토큰 삭제 로직
        cookieProvider.removeCookie(response, TEMPORARY_TOKEN);

        String accessToken = jwtTokenProvider.generateAccessToken(userDetails.getMemberId());
        cookieProvider.addCookie(response, ACCESS_TOKEN, accessToken);

        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails.getMemberId());
        cookieProvider.addCookie(response, REFRESH_TOKEN, refreshToken);

        respondCaseSuccess(response,null);

    }

    private void handleLogout(HttpServletResponse response) throws IOException {

        // TODO: Redis 토큰 삭제 로직
        cookieProvider.removeCookie(response, ACCESS_TOKEN);
        cookieProvider.removeCookie(response, REFRESH_TOKEN);
        respondCaseSuccess(response,null);
    }

    private void handleDefaultCase(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String accessToken = cookieProvider.getCookieValue(request, ACCESS_TOKEN);
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = cookieProvider.getCookieValue(request, REFRESH_TOKEN);
        authentication = jwtTokenProvider.getAuthentication(refreshToken);
        if (authentication != null) {
            accessToken = jwtTokenProvider.generateAccessToken(((CustomUserDetails) authentication.getPrincipal()).getMemberId());
            cookieProvider.addCookie(response, ACCESS_TOKEN, accessToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return;
        }
        respondCaseFail(response,REFRESH_TOKEN_EXPIRED);
    }

    private void respondCaseSuccess(HttpServletResponse response, Object responseDto) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = new ObjectMapper().writeValueAsString(responseDto);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

    private void respondCaseFail(HttpServletResponse response, ExceptionCode exceptionCode) throws IOException {
        response.setStatus(exceptionCode.getStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json = new ObjectMapper().writeValueAsString(new BusinessException(exceptionCode));
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

    private boolean isValidTotp(Long memberId, String totpCode) {
        // This method should implement the logic to verify a TOTP code.
        // Here we assume it is a placeholder.
        return totpService.verificationTotpCode(memberId, totpCode);
    }
}

