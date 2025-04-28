package com.todoapp.domain.factory.UserFactory;

import com.todoapp.domain.entities.user.User;
import com.todoapp.domain.enums.Role;

public class UserFactoryImpl implements IUserFactory {

    @Override
    public User create(String username, String email, String password, Role role) {
        return new User(username, email, password, role);
    }
}
