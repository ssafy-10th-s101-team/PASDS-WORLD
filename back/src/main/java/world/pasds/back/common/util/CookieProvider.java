package world.pasds.back.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {
    @Value("${cookie.path}")
    private String cookiePath;

    @Value("${cookie.secure}")
    private boolean cookieSecure;

    @Value("${cookie.httpOnly}")
    private boolean cookieHttpOnly;

//    @Value("${cookie.domain}")
//    private String cookieDomain;

    @Value("${cookie.sameSite}")
    private String cookieSameSite;

    public void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        String dynamicDomain = getDomainBasedOnRequest(request);
        ResponseCookie responseCookie = ResponseCookie.from(name, value)
                .maxAge(-1)
                .path(cookiePath)
                .secure(cookieSecure)
                .httpOnly(cookieHttpOnly)
                .domain(dynamicDomain)
                .sameSite(cookieSameSite)
                .build();
        response.addHeader("Set-Cookie", responseCookie.toString());
    }

    public void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        String dynamicDomain = getDomainBasedOnRequest(request);
        ResponseCookie responseCookie = ResponseCookie.from(name, null)
                .maxAge(0)
                .path(cookiePath)
                .secure(cookieSecure)
                .httpOnly(cookieHttpOnly)
                .domain(dynamicDomain)
                .sameSite(cookieSameSite)
                .build();
        response.addHeader("Set-Cookie", responseCookie.toString());
    }

    private String getDomainBasedOnRequest(HttpServletRequest request) {
        String host = request.getHeader("Host");
//        System.out.println(host);

//        String origin = request.getHeader("Origin");
//        System.out.println("Request Origin: " + origin);

        if (host != null && host.contains("localhost")) {
            return "localhost";
        }
        return ".pasds.world";
    }

    public String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;  // 쿠키를 찾지 못한 경우 null 반환
    }

}