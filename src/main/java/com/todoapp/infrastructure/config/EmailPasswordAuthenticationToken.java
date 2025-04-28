package com.todoapp.infrastructure.config;

import org.springframework.security.authentication.AbstractAuthenticationToken ;
import org.springframework.security.core.GrantedAuthority;


import java.util.Collection;



public class EmailPasswordAuthenticationToken extends AbstractAuthenticationToken  {
    private final Object principal; 
    private Object credentials; 

    // Giriş için kullanılan constructor (email ve şifre ile giriş)
    public EmailPasswordAuthenticationToken(String email, String password) {
        super(null);
        this.principal = email;
        this.credentials = password;
        setAuthenticated(false);
    }

    // Doğrulama sonrası kullanılan constructor (email ve roller)
    public EmailPasswordAuthenticationToken(Object principal, Object credentials,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
