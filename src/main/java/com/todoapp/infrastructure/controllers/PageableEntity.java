package com.todoapp.infrastructure.controllers;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableEntity<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private Long totalElement;
    private int totalPages;

}
