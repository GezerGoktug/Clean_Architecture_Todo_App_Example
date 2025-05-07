package com.todoapp.infrastructure.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.application.dto.base.ResponseDtoDefault;
import com.todoapp.application.dto.todo.RequestDtoTodo;
import com.todoapp.application.dto.todo.ResponseDtoTodo;
import com.todoapp.application.interactors.todo.TodoInputBoundary;
import com.todoapp.application.pagination.PageItem;
import com.todoapp.application.pagination.PageableRequest;
import com.todoapp.infrastructure.controllers.ITodoController;
import com.todoapp.infrastructure.controllers.PageableEntity;
import com.todoapp.infrastructure.controllers.RestBaseController;
import com.todoapp.infrastructure.controllers.RootEntity;
import com.todoapp.infrastructure.controllers.rate_limiter.RateLimited;
import com.todoapp.infrastructure.dto.todo.CreateTodoRequest;
import com.todoapp.infrastructure.dto.todo.UpdateTodoRequest;
import com.todoapp.infrastructure.util.DtoMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/user/todo")
@CrossOrigin(origins = "http://localhost:4000")
public class TodoControllerImpl extends RestBaseController implements ITodoController {

    @Autowired
    private TodoInputBoundary todoInput;

    @Override
    @GetMapping("/list")
    @RateLimited(maxRequests = 10, timeWindowSeconds = 20)
    @Cacheable(value = "my_todos", key = "#root.methodName + '_page_' + #pageableRequest.pageNumber + '_size_' + #pageableRequest.pageSize", unless = "#result == null")
    public RootEntity<PageableEntity<ResponseDtoTodo>> findMyTodos(PageableRequest pageableRequest) {
        PageItem<ResponseDtoTodo> responseDtoTodo = todoInput.findMyTodos(pageableRequest);
        return ok(toPageableResponse(responseDtoTodo), 200);
    }

    @Override
    @GetMapping("/{todoId}")
    @RateLimited(maxRequests = 10, timeWindowSeconds = 20)
    @Cacheable(cacheNames = "todo_id", key = "#root.methodName + #todoId", unless = "#result == null")
    public RootEntity<ResponseDtoTodo> getTodoById(
            @PathVariable(name = "todoId", required = true) String todoId) {
        return ok(todoInput.findTodoById(todoId), 200);
    }

    @Override
    @PostMapping("/add")
    @CacheEvict(value = { "todo_id", "my_todos" }, allEntries = true)
    public RootEntity<ResponseDtoDefault> createTodo(@Valid @RequestBody CreateTodoRequest request) {
        return ok(todoInput.createTodoForMy(DtoMapper.map(request, RequestDtoTodo.class)), 200);
    }

    @Override
    @DeleteMapping("/{todoId}")
    @CacheEvict(value = { "todo_id", "my_todos" }, allEntries = true)
    public RootEntity<ResponseDtoDefault> removeTodo(
            @PathVariable(name = "todoId", required = true) String todoId) {
        return ok(todoInput.removeTodo(todoId), 200);
    }

    @Override
    @PutMapping("/{todoId}")
    @CachePut(cacheNames = "todo_id", key = "'getTodoById' + #todoId", unless = "#result == null")
    public RootEntity<ResponseDtoTodo> updateTodo(@Valid @RequestBody UpdateTodoRequest request,
            @PathVariable(name = "todoId", required = true) String todoId) {
        return ok(todoInput.updateTodoForMy(DtoMapper.map(request, RequestDtoTodo.class), todoId), 200);
    }

}
