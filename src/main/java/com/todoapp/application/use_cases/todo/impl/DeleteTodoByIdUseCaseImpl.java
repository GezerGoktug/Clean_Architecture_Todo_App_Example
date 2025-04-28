package com.todoapp.application.use_cases.todo.impl;

import com.todoapp.application.ds_gateways.TodoDsGateway;
import com.todoapp.application.dto.base.ResponseDtoDefault;
import com.todoapp.application.use_cases.todo.IDeleteTodoByIdUseCase;

public class DeleteTodoByIdUseCaseImpl implements IDeleteTodoByIdUseCase {

    private final TodoDsGateway todoDsGateway;

    public DeleteTodoByIdUseCaseImpl(TodoDsGateway todoDsGateway) {
        this.todoDsGateway = todoDsGateway;
    }

    private ResponseDtoDefault toResponseDTO(String message) {
        ResponseDtoDefault responseDtoDefault = new ResponseDtoDefault();

        responseDtoDefault.setMessage(message);

        return responseDtoDefault;
    }

    @Override
    public ResponseDtoDefault execute(String todoId) {

        todoDsGateway.deleteTodo(todoId);

        return toResponseDTO("Başarıyla todo silinmiştir");
    }

}
