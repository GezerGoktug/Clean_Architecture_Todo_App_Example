package com.todoapp.infrastructure.data_factory;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.todoapp.application.ds_gateways.TodoDsGateway;
import com.todoapp.application.ds_gateways.dto.TodoDataRequestDTO;
import com.todoapp.application.ds_gateways.dto.TodoDataResponseDTO;
import com.todoapp.application.ds_gateways.dto.UserDataResponseDTO;
import com.todoapp.application.exceptions.BaseException;
import com.todoapp.application.exceptions.ErrorMessage;
import com.todoapp.application.exceptions.MessageType;
import com.todoapp.application.pagination.PageItem;
import com.todoapp.application.pagination.PageableRequest;
import com.todoapp.infrastructure.data_mappers.TodoDataMapper;
import com.todoapp.infrastructure.data_mappers.UserDataMapper;
import com.todoapp.infrastructure.repositories.TodoRepository;
import com.todoapp.infrastructure.util.PagerUtil;

@Component
public class TodoDataFactory implements TodoDsGateway {

    @Autowired
    private TodoRepository todoRepository;

    private TodoDataResponseDTO convertToDTO(TodoDataMapper entity) {
        TodoDataResponseDTO todoDto = new TodoDataResponseDTO();
        UserDataResponseDTO userDto = new UserDataResponseDTO();
        BeanUtils.copyProperties(entity, todoDto);
        BeanUtils.copyProperties(entity.getUser(), userDto);
        userDto.setRole(entity.getUser().getRole().toString());
        todoDto.setUser(userDto);
        return todoDto;
    }

    public PageItem<TodoDataResponseDTO> getTodosByUserId(String userId, PageableRequest pageableRequest) {

        Pageable pageable = PagerUtil.toPageable(pageableRequest);

        Page<TodoDataMapper> dbTodos = todoRepository.findByUserId(UUID.fromString(userId), pageable);

        Page<TodoDataResponseDTO> dtoPage = dbTodos.map(this::convertToDTO);

        PageItem<TodoDataResponseDTO> pageItem = new PageItem(dtoPage.getContent(),
                dtoPage.getPageable().getPageNumber(), dtoPage.getPageable().getPageSize(), dtoPage.getTotalElements(),
                dtoPage.getTotalPages());

        return pageItem;
    }

    public TodoDataResponseDTO getTodoById(String todoId) {
        Optional<TodoDataMapper> todoDataMapper = todoRepository.findById(UUID.fromString(todoId));
        if (todoDataMapper.isPresent())
            return convertToDTO(todoDataMapper.get());
        else
            return null;
    }

    public void createTodo(TodoDataRequestDTO todoDataRequestDTO) {
        try {

            TodoDataMapper todoDataMapper = new TodoDataMapper();

            UserDataMapper userDataMapper = new UserDataMapper();

            BeanUtils.copyProperties(todoDataRequestDTO, todoDataMapper);
            BeanUtils.copyProperties(todoDataRequestDTO.getUser(), userDataMapper);

            todoDataMapper.setUser(userDataMapper);

            todoRepository.save(todoDataMapper);
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(e.getMessage(), MessageType.TODO_CREATE_ERROR), 500);
        }
    }

    public void deleteTodo(String todoId) {
        todoRepository.deleteById(UUID.fromString(todoId));
    }

    public TodoDataResponseDTO updateTodo(TodoDataRequestDTO todoDataRequestDTO, String todoId) {
        try {

            TodoDataMapper todoDataMapper = new TodoDataMapper();

            UserDataMapper userDataMapper = new UserDataMapper();

            BeanUtils.copyProperties(todoDataRequestDTO, todoDataMapper);
            BeanUtils.copyProperties(todoDataRequestDTO.getUser(), userDataMapper);

            todoDataMapper.setUser(userDataMapper);

            todoDataMapper.setId(UUID.fromString(todoId));

            TodoDataMapper updatedTodo = todoRepository.save(todoDataMapper);

            return convertToDTO(updatedTodo);
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(e.getMessage(), MessageType.TODO_CREATE_ERROR), 500);
        }
    }
}
