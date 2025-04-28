package com.todoapp.domain.factory.TodoFactory;

import com.todoapp.domain.entities.todo.Todo;
import com.todoapp.domain.entities.user.AppUser;

public class TodoFactoryImpl implements ITodoFactory {

    @Override
    public Todo create(String title, String desc, AppUser user) {
        return new Todo(title, desc, user);
    }

}
