package com.todoapp.application.exceptions;

import java.util.HashMap;

public class NotValidException extends RuntimeException {

    private Integer statusCode;
    private HashMap errorMap;

    public NotValidException(HashMap errorMap, Integer statusCode) {
        this.errorMap = errorMap;
        this.statusCode = statusCode;
    }

    public HashMap getErrorMap() {
        return errorMap;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
