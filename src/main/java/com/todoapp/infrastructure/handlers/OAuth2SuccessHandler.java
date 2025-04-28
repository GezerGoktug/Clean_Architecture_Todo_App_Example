package com.todoapp.infrastructure.handlers;

import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import org.springframework.stereotype.Component;

import com.todoapp.application.interactors.auth.AuthInputBoundary;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthInputBoundary authService;

    public OAuth2SuccessHandler(AuthInputBoundary authService) {
        this.authService = authService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String accessToken = authService.oauth2Login(oAuth2User.getAttributes());
        String redirectUrl = "http://localhost:4000?access_token=" + accessToken;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

}