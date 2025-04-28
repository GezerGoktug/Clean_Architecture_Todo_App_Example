package com.todoapp.infrastructure.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.todoapp.application.exceptions.BaseException;
import com.todoapp.application.exceptions.NotValidException;

//! Controller da error ları yakalar .Error middleware gibi
@ControllerAdvice
public class GlobalExceptionHandler {

    // ! Normal hatalar için bu kullanılır
    @ExceptionHandler(value = { BaseException.class })
    public ResponseEntity<ApiError<?>> handleBaseException(BaseException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ApiErrorFactory.createApiError(ex.getMessage(), ex.getStatusCode(),
                request.getDescription(false).substring(4)));
    }

    // ! Domain katmanı validasyon hatalar için bu kullanılır
    @ExceptionHandler(value = { NotValidException.class })
    public ResponseEntity<ApiError<?>> handleNotValidException(NotValidException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ApiErrorFactory.createApiError(ex.getErrorMap(), ex.getStatusCode(),
                request.getDescription(false).substring(4)));
    }

    // ! Spring validation hataları için kullanılır
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleMethodArgumentException(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        // ? error:["","",""] tarzı
        Map<String, List<String>> hashMap = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();

            if (hashMap.containsKey(fieldName)) {
                hashMap.put(fieldName, addToList(hashMap.get(fieldName), ((FieldError) err).getDefaultMessage()));
            } else {
                hashMap.put(fieldName, addToList(new ArrayList<>(), ((FieldError) err).getDefaultMessage()));
            }
        });

        return ResponseEntity.badRequest().body(ApiErrorFactory.createApiError(hashMap, ex.getStatusCode().value(),
                request.getDescription(false).substring(4)));
    }

    private List<String> addToList(List<String> list, String value) {
        list.add(value);
        return list;
    }

}
