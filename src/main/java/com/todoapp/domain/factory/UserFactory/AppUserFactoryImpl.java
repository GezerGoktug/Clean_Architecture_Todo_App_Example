package com.todoapp.domain.factory.UserFactory;

import java.util.Date;
import java.util.UUID;

import com.todoapp.domain.entities.user.AppUser;
import com.todoapp.domain.enums.Role;

public class AppUserFactoryImpl implements IAppUserFactory {

    @Override
    public AppUser create(UUID id, String username, String email, String role, Date createdAt) {
        return new AppUser(id, createdAt, username, email, Role.valueOf(role));
    }
}
