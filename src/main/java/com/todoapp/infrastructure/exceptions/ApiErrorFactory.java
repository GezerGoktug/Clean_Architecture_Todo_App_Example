package com.todoapp.infrastructure.exceptions;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;

public class ApiErrorFactory {

    private static String getLocalHost() {
        try {
            return Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <E> ApiError<E> createApiError(E message, Integer statusCode, String path) {
        ApiError<E> apiError = new ApiError<E>();

        Exception<E> exception = new Exception<>();

        apiError.setStatus(statusCode);

        exception.setCreatedAt(new Date());
        exception.setMessage(message);
        exception.setPath(path);
        exception.setHostName(getLocalHost());

        apiError.setException(exception);

        return apiError;
    }

}
