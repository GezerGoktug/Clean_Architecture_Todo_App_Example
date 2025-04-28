package com.todoapp.infrastructure.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    // ! Custom Authentication Provider
    @Bean
    public AuthenticationProvider emailPasswordAuthenticationProvider() {
        return new EmailPasswordAuthenticationProvider(userDetailsService, passwordEncoder());
    }

    // ! AuthenticationManager bean'ini burada oluşturuyoruz.
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(emailPasswordAuthenticationProvider()));
    }

    // ! Bcrpyt password encoder başlatma
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
