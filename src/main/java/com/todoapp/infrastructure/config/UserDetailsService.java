package com.todoapp.infrastructure.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.todoapp.application.ds_gateways.UserDsGateway;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserDsGateway userDsGateway;

    public UserDetails createUserDetails(String id,String email,String username, String password, String role) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        return new UserDetails(id,email,username, password == null ? "" : password, authorities);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, String> userMap = userDsGateway.getUserWithUsername(username);
        return createUserDetails(userMap.get("userId"),userMap.get("email"),userMap.get("username"), userMap.get("password"), userMap.get("role"));
    }

    public UserDetails loadUserByEmail(String email) {
        Map<String, String> userMap = userDsGateway.loadUserByEmail(email);
        return createUserDetails(userMap.get("userId"),email,userMap.get("username"), userMap.get("password"), userMap.get("role"));
    }
}
