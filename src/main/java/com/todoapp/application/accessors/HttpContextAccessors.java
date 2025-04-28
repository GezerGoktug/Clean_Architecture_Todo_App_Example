package com.todoapp.application.accessors;

public interface HttpContextAccessors {
    void setCookie(String key, String value);

    void clearCookie(String key);

    String getCookie(String key);

    void clearSession();
}
