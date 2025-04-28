package com.todoapp.domain.factory.UserFactory;

import com.todoapp.domain.entities.user.User;
import com.todoapp.domain.enums.Role;

public interface IUserFactory {

    User create(String username, String email, String password,Role role);
}
