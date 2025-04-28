package com.todoapp.infrastructure.handlers;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.todoapp.application.accessors.HttpContextAccessors;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class HttpContextHandler implements HttpContextAccessors {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public HttpContextHandler(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void setCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(1000 * 60 * 60 * 24 * 15);

        response.addCookie(cookie);
    }

    public void clearCookie(String key) {
        Cookie cookie = new Cookie(key, null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }

    public String getCookie(String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void clearSession() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

}
