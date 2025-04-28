package com.todoapp.infrastructure.controllers;

import org.springframework.data.domain.Pageable;

import com.todoapp.application.pagination.PageItem;
import com.todoapp.application.pagination.PageableRequest;
import com.todoapp.infrastructure.util.PagerUtil;

public class RestBaseController {

    public <T> RootEntity<T> ok(T data, Integer status) {
        return RootEntity.ok(data, status);
    }

    public <T> RootEntity<T> error(String error, Integer status) {
        return RootEntity.error(error, status);
    }

    public Pageable toPageable(PageableRequest pageable) {
        return PagerUtil.toPageable(pageable);
    }

    public <T> PageableEntity<T> toPageableResponse(PageItem<T> page) {
        return PagerUtil.toPageableResponse(page);
    }

}
