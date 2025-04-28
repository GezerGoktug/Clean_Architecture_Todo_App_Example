package com.todoapp.domain.factory.TodoFactory;


import com.todoapp.domain.entities.todo.Todo;
import com.todoapp.domain.entities.user.AppUser;

public interface ITodoFactory {

    Todo create(String title, String desc, AppUser user);


}
