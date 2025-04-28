package com.todoapp.application.interactors.todo.impl;

import com.todoapp.application.accessors.AuthenticationAccessors;
import com.todoapp.application.dto.base.ResponseDtoDefault;
import com.todoapp.application.dto.todo.RequestDtoTodo;
import com.todoapp.application.dto.todo.ResponseDtoTodo;
import com.todoapp.application.interactors.todo.TodoInputBoundary;
import com.todoapp.application.pagination.PageItem;
import com.todoapp.application.pagination.PageableRequest;
import com.todoapp.application.use_cases.todo.ICreateTodoByUserIdUseCase;
import com.todoapp.application.use_cases.todo.IDeleteTodoByIdUseCase;
import com.todoapp.application.use_cases.todo.IGetTodosUseCase;
import com.todoapp.application.use_cases.todo.IUpdateTodoByIdUseCase;

public class TodoInteractor implements TodoInputBoundary {

    private final IGetTodosUseCase getTodosByUserIdUseCase;

    private final ICreateTodoByUserIdUseCase createTodoUseCase;

    private final IDeleteTodoByIdUseCase deleteTodoByIdUseCase;

    private final IUpdateTodoByIdUseCase updateTodoByIdUseCase;

    private final AuthenticationAccessors authenticationManager;

    public TodoInteractor(IGetTodosUseCase getTodosByUserIdUseCase, ICreateTodoByUserIdUseCase createTodoUseCase,
            IDeleteTodoByIdUseCase deleteTodoByIdUseCase, IUpdateTodoByIdUseCase updateTodoByIdUseCase,
            AuthenticationAccessors authenticationAccessors) {
        this.getTodosByUserIdUseCase = getTodosByUserIdUseCase;
        this.createTodoUseCase = createTodoUseCase;
        this.deleteTodoByIdUseCase = deleteTodoByIdUseCase;
        this.updateTodoByIdUseCase = updateTodoByIdUseCase;
        this.authenticationManager = authenticationAccessors;
    }

    public PageItem<ResponseDtoTodo> findMyTodos(PageableRequest pageableRequest) {
        String userId = authenticationManager.getUserId();
        return getTodosByUserIdUseCase.execute(userId, pageableRequest);
    }

    public ResponseDtoDefault createTodoForMy(RequestDtoTodo requestDtoTodo) {
        String userId = authenticationManager.getUserId();
        return createTodoUseCase.execute(userId, requestDtoTodo);
    }

    @Override
    public ResponseDtoDefault removeTodo(String todoId) {
        return deleteTodoByIdUseCase.execute(todoId);
    }

    @Override
    public ResponseDtoTodo updateTodoForMy(RequestDtoTodo request, String todoId) {
        String userId = authenticationManager.getUserId();
        return updateTodoByIdUseCase.execute(request, todoId, userId);
    }

    @Override
    public ResponseDtoTodo findTodoById(String todoId) {
        return getTodosByUserIdUseCase.execute(todoId);
    }
}
