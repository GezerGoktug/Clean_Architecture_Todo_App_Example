package com.todoapp.application.interactors.todo;

import com.todoapp.application.dto.base.ResponseDtoDefault;
import com.todoapp.application.dto.todo.RequestDtoTodo;
import com.todoapp.application.dto.todo.ResponseDtoTodo;
import com.todoapp.application.pagination.PageItem;
import com.todoapp.application.pagination.PageableRequest;

public interface TodoInputBoundary {
    PageItem<ResponseDtoTodo> findMyTodos(PageableRequest pageableRequest);

    ResponseDtoDefault createTodoForMy(RequestDtoTodo requestDtoTodo);

    ResponseDtoDefault removeTodo(String todoId);

    ResponseDtoTodo updateTodoForMy(RequestDtoTodo request, String todoId);

    ResponseDtoTodo findTodoById(String todoId);
}
