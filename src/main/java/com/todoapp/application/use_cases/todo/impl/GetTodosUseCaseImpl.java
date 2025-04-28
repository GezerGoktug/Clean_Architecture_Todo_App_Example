package com.todoapp.application.use_cases.todo.impl;

import com.todoapp.application.ds_gateways.TodoDsGateway;
import com.todoapp.application.ds_gateways.dto.TodoDataResponseDTO;
import com.todoapp.application.dto.todo.ResponseDtoTodo;
import com.todoapp.application.dto.user.ResponseDtoUser;
import com.todoapp.application.pagination.PageItem;
import com.todoapp.application.pagination.PageableRequest;
import com.todoapp.application.use_cases.todo.IGetTodosUseCase;
import com.todoapp.application.utils.ClassUtils;

public class GetTodosUseCaseImpl implements IGetTodosUseCase {

    private final TodoDsGateway todoDsGateway;

    public GetTodosUseCaseImpl(TodoDsGateway todoDsGateway) {
        this.todoDsGateway = todoDsGateway;
    }

    private ResponseDtoTodo toResponseDTO(TodoDataResponseDTO todoDataResponseDTO) {
        ResponseDtoTodo dto = new ResponseDtoTodo();
        ResponseDtoUser dtoUser = new ResponseDtoUser();
        ClassUtils.copyProperties(todoDataResponseDTO, dto);
        ClassUtils.copyProperties(todoDataResponseDTO.getUser(), dtoUser);
        dto.setUser(dtoUser);
        return dto;
    }

    public PageItem<ResponseDtoTodo> execute(String userId, PageableRequest pageableRequest) {
        PageItem<TodoDataResponseDTO> todoDataResponseDto = todoDsGateway.getTodosByUserId(userId, pageableRequest);
        return todoDataResponseDto.map(this::toResponseDTO);
    }

    public ResponseDtoTodo execute(String todoId) {
        return toResponseDTO(todoDsGateway.getTodoById(todoId));
    }

}
