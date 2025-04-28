package com.todoapp.application.use_cases.todo.impl;

import java.util.HashMap;

import com.todoapp.application.ds_gateways.TodoDsGateway;
import com.todoapp.application.ds_gateways.UserDsGateway;
import com.todoapp.application.ds_gateways.dto.TodoDataRequestDTO;
import com.todoapp.application.ds_gateways.dto.TodoUserDataRequestDTO;
import com.todoapp.application.ds_gateways.dto.UserDataResponseDTO;
import com.todoapp.application.dto.base.ResponseDtoDefault;
import com.todoapp.application.dto.todo.RequestDtoTodo;
import com.todoapp.application.exceptions.NotValidException;
import com.todoapp.application.use_cases.todo.ICreateTodoByUserIdUseCase;
import com.todoapp.application.utils.ClassUtils;
import com.todoapp.domain.entities.todo.Todo;
import com.todoapp.domain.entities.user.AppUser;
import com.todoapp.domain.factory.TodoFactory.ITodoFactory;
import com.todoapp.domain.factory.UserFactory.IAppUserFactory;

public class CreateTodoByUserIdUseCaseImpl implements ICreateTodoByUserIdUseCase {

    private final ITodoFactory todoFactory;

    private final IAppUserFactory appUserFactory;

    private final UserDsGateway userDsGateway;

    private final TodoDsGateway todoDsGateway;

    public CreateTodoByUserIdUseCaseImpl(ITodoFactory todoFactory, IAppUserFactory appUserFactory,
            UserDsGateway userDsGateway, TodoDsGateway todoDsGateway) {
        this.todoFactory = todoFactory;
        this.appUserFactory = appUserFactory;
        this.userDsGateway = userDsGateway;
        this.todoDsGateway = todoDsGateway;
    }

    private TodoDataRequestDTO convertDataRequestDTO(Todo todo) {
        TodoDataRequestDTO todoDataRequestDTO = new TodoDataRequestDTO();
        TodoUserDataRequestDTO userDataRequestDTO = new TodoUserDataRequestDTO();
        ClassUtils.copyProperties(todo, todoDataRequestDTO);
        ClassUtils.copyProperties(todo.getUser(), userDataRequestDTO);
        todoDataRequestDTO.setUser(userDataRequestDTO);
        return todoDataRequestDTO;
    }

    private ResponseDtoDefault toResponseDTO(String message) {
        ResponseDtoDefault responseDtoDefault = new ResponseDtoDefault();

        responseDtoDefault.setMessage(message);

        return responseDtoDefault;
    }

    @Override
    public ResponseDtoDefault execute(String userId, RequestDtoTodo requestDtoTodo) {

        UserDataResponseDTO userDataResponseDTO = userDsGateway.getUserWithUserId(userId);

        AppUser user = appUserFactory.create(userDataResponseDTO.getId(), userDataResponseDTO.getUsername(),
                userDataResponseDTO.getEmail(), userDataResponseDTO.getRole(), userDataResponseDTO.getCreatedAt());

        Todo newTodo = todoFactory.create(requestDtoTodo.getTitle(), requestDtoTodo.getDesc(), user);

        HashMap<String, Object> errorMap = newTodo.validateTodo();

        if (errorMap.size() > 0)
            throw new NotValidException(errorMap, 400);

        todoDsGateway.createTodo(convertDataRequestDTO(newTodo));

        return toResponseDTO("Başarıyla todo oluşturulmuştur");

    }
}
