package com.todoapp.application.ds_gateways.dto;

import java.util.UUID;

public class TodoUserDataRequestDTO extends BaseDataRequestDTO{
    
    private UUID id;

    private String email;

    private String username;

    private String role;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
    
}
