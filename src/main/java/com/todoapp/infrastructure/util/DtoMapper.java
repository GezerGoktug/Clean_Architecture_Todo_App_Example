package com.todoapp.infrastructure.util;

import org.springframework.beans.BeanUtils;

import com.todoapp.application.exceptions.BaseException;
import com.todoapp.application.exceptions.ErrorMessage;
import com.todoapp.application.exceptions.MessageType;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoMapper {

    public <S, T> T map(S source, Class<T> targetClass) {
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(null, MessageType.DTO_CONVERT_ERROR), 500);
        }
    }
}
