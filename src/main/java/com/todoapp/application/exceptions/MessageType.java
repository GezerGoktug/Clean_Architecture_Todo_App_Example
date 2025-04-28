package com.todoapp.application.exceptions;

public enum MessageType {
    NO_RECORD_EXIST("Not found anything record"),
    JWT_TOKEN_EXPIRED_DATE("Jwt token expired date"),
    USERNAME_NOT_FOUND("Username not found"),
    EMAIL_NOT_FOUND("Email not found"),
    EMAIL_ALREADY_USED("Email already used"),
    EMAIL_OR_PASSWORD_INVALID("Email or password invalid"),
    REFRESH_TOKEN_NOT_FOUND("Refresh token not found"),
    REFRESH_TOKEN_EXPIRED_DATE("Refresh token expired date"),
    DTO_CONVERT_ERROR("Dto converting error"),
    TODO_CREATE_ERROR("An error occuring when todo creating."),
    UNAUTHORIZED("Unauthorized"),
    GENERAL_ERROR("Internal server error");

    private String message;

    MessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
