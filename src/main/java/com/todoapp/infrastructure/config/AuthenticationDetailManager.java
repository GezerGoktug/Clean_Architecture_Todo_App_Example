package com.todoapp.infrastructure.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.todoapp.application.accessors.AuthenticationAccessors;
import com.todoapp.application.exceptions.BaseException;
import com.todoapp.application.exceptions.ErrorMessage;
import com.todoapp.application.exceptions.MessageType;

@Component
public class AuthenticationDetailManager implements AuthenticationAccessors {

    private final AuthenticationProvider authenticationProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationDetailManager(AuthenticationProvider authenticationProvider,
            BCryptPasswordEncoder passwordEncoder) {
        this.authenticationProvider = authenticationProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public void setAuthentication(String email, String password) {
        EmailPasswordAuthenticationToken authenticationToken = new EmailPasswordAuthenticationToken(
                email, password);

        authenticationProvider.authenticate(authenticationToken);
    }

    private UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object details = authentication.getDetails();

            UserDetails userDetails = (UserDetails) details;

            userDetails.getAuthorities().stream().forEach(item -> System.out.println(item));
            return userDetails;
        }

        throw new BaseException(new ErrorMessage(null, MessageType.UNAUTHORIZED), 401);
    }

    public void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }

    @Override
    public String getUserId() {
        return getUserDetails().getId();
    }

    public String getPassword() {
        return getUserDetails().getPassword();
    }

    @Override
    public String getUsername() {
        return getUserDetails().getUsername();
    }

    @Override
    public String getEmail() {
        return getUserDetails().getEmail();
    }

    @Override
    public String getRole() {
        return getUserDetails().getAuthorities().stream().findFirst().toString();
    }

}