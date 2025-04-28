
package com.todoapp.application.exceptions;

public class BaseException extends RuntimeException {

    private Integer statusCode;

    public BaseException(ErrorMessage errorMessage, Integer statusCode) {
        super(errorMessage.prepareErrorMessage());

        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

}