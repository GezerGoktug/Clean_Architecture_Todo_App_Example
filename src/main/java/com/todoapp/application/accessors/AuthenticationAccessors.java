package com.todoapp.application.accessors;

public interface AuthenticationAccessors {

    void setAuthentication(String email, String password);

    void clearAuthentication();

    String hashPassword(String password);

    String getUserId();

    String getUsername();

    String getEmail();

    String getRole();

    String getPassword();
}
