package com.todoapp.application.use_cases.todo;

import com.todoapp.application.dto.todo.RequestDtoTodo;
import com.todoapp.application.dto.todo.ResponseDtoTodo;

public interface IUpdateTodoByIdUseCase {
    
    ResponseDtoTodo execute(RequestDtoTodo request,String todoId,String userId);
}
