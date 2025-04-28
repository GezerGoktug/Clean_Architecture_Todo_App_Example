package com.todoapp.domain.entities.user;

import java.util.Date;
import java.util.UUID;

import com.todoapp.domain.enums.Role;

public class AppUser {

    private UUID id;

    private Date createdAt;

    private String username;

    private String email;

    private Role role;

    public AppUser(UUID id, Date createdAt, String username, String email, Role role) {
        this.id = id;
        this.createdAt = createdAt;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

}
