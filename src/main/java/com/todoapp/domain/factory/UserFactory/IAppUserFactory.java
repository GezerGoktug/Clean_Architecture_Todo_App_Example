package com.todoapp.domain.factory.UserFactory;

import java.util.Date;
import java.util.UUID;

import com.todoapp.domain.entities.user.AppUser;

public interface IAppUserFactory {

    AppUser create(UUID id, String username, String email, String role, Date createdAt);
}
