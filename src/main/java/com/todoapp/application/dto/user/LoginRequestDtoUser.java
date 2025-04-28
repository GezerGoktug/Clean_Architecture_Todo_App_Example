package com.todoapp.application.dto.user;

public class LoginRequestDtoUser {

    private String email;
    private String password;

    public LoginRequestDtoUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequestDtoUser() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
