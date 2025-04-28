package com.todoapp.application.use_cases.todo;


import com.todoapp.application.dto.todo.ResponseDtoTodo;
import com.todoapp.application.pagination.PageItem;
import com.todoapp.application.pagination.PageableRequest;

public interface IGetTodosUseCase {
    PageItem<ResponseDtoTodo> execute(String userId,PageableRequest pageableRequest);
    ResponseDtoTodo execute(String todoId);
}
