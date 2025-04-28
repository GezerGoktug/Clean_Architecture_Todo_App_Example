package com.todoapp.application.use_cases.todo;

import com.todoapp.application.dto.base.ResponseDtoDefault;
import com.todoapp.application.dto.todo.RequestDtoTodo;

public interface ICreateTodoByUserIdUseCase {

    ResponseDtoDefault execute(String userId,RequestDtoTodo requestDtoTodo);
}
