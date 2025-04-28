package com.todoapp.application.ds_gateways;

import com.todoapp.application.ds_gateways.dto.TodoDataRequestDTO;
import com.todoapp.application.ds_gateways.dto.TodoDataResponseDTO;
import com.todoapp.application.pagination.PageItem;
import com.todoapp.application.pagination.PageableRequest;

public interface TodoDsGateway {

    PageItem<TodoDataResponseDTO> getTodosByUserId(String userId, PageableRequest pageableRequest);

    void createTodo(TodoDataRequestDTO todoDataRequestDTO);

    void deleteTodo(String todoId);

    TodoDataResponseDTO updateTodo(TodoDataRequestDTO todoDataRequestDTO, String todoId);

    TodoDataResponseDTO getTodoById(String todoId);
}
