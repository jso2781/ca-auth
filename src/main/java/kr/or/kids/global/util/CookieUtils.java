package kr.or.kids.global.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieUtils {

    public void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public Optional<String> getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return Optional.empty();
        return Arrays.stream(request.getCookies())
                .filter(c -> name.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }

    public void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        if (request.getCookies() != null) {
            Arrays.stream(request.getCookies())
                    .filter(c -> name.equals(c.getName()))
                    .forEach(c -> {
                        c.setMaxAge(0);
                        c.setPath("/");
                        response.addCookie(c);
                    });
        }
    }
}