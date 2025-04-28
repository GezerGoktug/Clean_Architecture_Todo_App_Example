package com.todoapp.infrastructure.util;


import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.todoapp.application.pagination.PageItem;
import com.todoapp.application.pagination.PageableRequest;
import com.todoapp.infrastructure.controllers.PageableEntity;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PagerUtil {

    public boolean isNullOrEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public Pageable toPageable(PageableRequest pageableRequest) {
        if (!isNullOrEmpty(pageableRequest.getColumnName())) {
            String columnName = pageableRequest.getColumnName();
            Sort sortBy = pageableRequest.isAsc() ? Sort.by(Direction.ASC, columnName)
                    : Sort.by(Direction.DESC, columnName);
            return PageRequest.of(pageableRequest.getPageNumber(), pageableRequest.getPageSize(), sortBy);
        }
        return PageRequest.of(pageableRequest.getPageNumber(), pageableRequest.getPageSize());
    }

    public <T> PageableEntity<T> toPageableResponse(PageItem<T> page) {
        PageableEntity<T> pageableEntity = new PageableEntity<>();
        BeanUtils.copyProperties(page, pageableEntity);
        return pageableEntity;
    }
}
