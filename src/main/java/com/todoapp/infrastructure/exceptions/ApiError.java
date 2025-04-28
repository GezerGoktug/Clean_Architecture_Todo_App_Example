package com.todoapp.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiError<E> {

    private Integer status;

    private Exception<E> exception;

}
