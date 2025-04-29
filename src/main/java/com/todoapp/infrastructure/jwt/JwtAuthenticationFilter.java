package com.todoapp.infrastructure.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.todoapp.application.exceptions.BaseException;
import com.todoapp.application.exceptions.ErrorMessage;
import com.todoapp.application.exceptions.MessageType;
import com.todoapp.infrastructure.config.EmailPasswordAuthenticationToken;
import com.todoapp.infrastructure.config.UserDetails;
import com.todoapp.infrastructure.config.UserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Value("${ACCESS_TOKEN_SECRET_KEY}")
    private String ACCESS_TOKEN_SECRET_KEY;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String email;

        String accessToken = header.substring(7);

        try {
            email = jwtService.getClaimsByKey(accessToken, ACCESS_TOKEN_SECRET_KEY, "email").toString();
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByEmail(email);
                if (userDetails != null && jwtService.isValidToken(accessToken, ACCESS_TOKEN_SECRET_KEY)) {
                    EmailPasswordAuthenticationToken authenticationToken = new EmailPasswordAuthenticationToken(
                            email, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(userDetails);

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }
            }
        } catch (ExpiredJwtException e) {
            throw new BaseException(new ErrorMessage(e.getMessage(), MessageType.TOKEN_EXPIRED_DATE), 401);
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(e.getMessage(), MessageType.GENERAL_ERROR), 500);
        }
        filterChain.doFilter(request, response);

    }

}
