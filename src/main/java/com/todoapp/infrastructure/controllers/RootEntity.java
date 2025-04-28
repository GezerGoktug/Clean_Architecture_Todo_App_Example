package com.todoapp.infrastructure.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class RootEntity<T> {

    private Integer status;

    private T data;

    private String errorMessage;

    public static <T> RootEntity<T> ok(T data, Integer status) {

        RootEntity<T> rootEntity = new RootEntity<T>();

        rootEntity.setStatus(status);
        rootEntity.setErrorMessage(null);
        rootEntity.setData(data);

        return rootEntity;
    }

    public static <T> RootEntity<T> error(String error, Integer status) {

        RootEntity<T> rootEntity = new RootEntity<T>();

        rootEntity.setStatus(status);
        rootEntity.setErrorMessage(error);
        rootEntity.setData(null);

        return rootEntity;
    }
}
