package world.pasds.back.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {
    @Value("${cookie.path}")
    private String cookiePath;

    @Value("${cookie.secure}")
    private boolean cookieSecure;

    @Value("${cookie.httpOnly}")
    private boolean cookieHttpOnly;

    public void addCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(-1);
        cookie.setPath(cookiePath);
        cookie.setSecure(cookieSecure);
        cookie.setHttpOnly(cookieHttpOnly);
        response.addCookie(cookie);
    }

    public void removeCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath(cookiePath);
        cookie.setSecure(cookieSecure);
        cookie.setHttpOnly(cookieHttpOnly);
        response.addCookie(cookie);
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