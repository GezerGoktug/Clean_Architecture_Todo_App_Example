package com.todoapp.infrastructure.controllers;

import com.todoapp.application.dto.base.ResponseDtoDefault;
import com.todoapp.application.dto.todo.ResponseDtoTodo;
import com.todoapp.application.pagination.PageableRequest;
import com.todoapp.infrastructure.dto.todo.CreateTodoRequest;
import com.todoapp.infrastructure.dto.todo.UpdateTodoRequest;

public interface ITodoController {

    RootEntity<PageableEntity<ResponseDtoTodo>> findMyTodos(PageableRequest pageableRequest);

    RootEntity<ResponseDtoDefault> createTodo(CreateTodoRequest request);

    RootEntity<ResponseDtoDefault> removeTodo(String todoId);

    RootEntity<ResponseDtoTodo> updateTodo(UpdateTodoRequest request, String todoId);

    RootEntity<ResponseDtoTodo> getTodoById(String todoId);
}
