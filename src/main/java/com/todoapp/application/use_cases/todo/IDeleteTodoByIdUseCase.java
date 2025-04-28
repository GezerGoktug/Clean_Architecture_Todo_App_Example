package com.todoapp.application.use_cases.todo;

import com.todoapp.application.dto.base.ResponseDtoDefault;

public interface IDeleteTodoByIdUseCase {
    
    ResponseDtoDefault execute(String todoId);

}
