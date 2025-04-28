package com.todoapp.application.use_cases.auth;

import com.todoapp.application.dto.user.LoginRequestDtoUser;
import com.todoapp.application.dto.user.AuthResponseDtoUser;

public interface ILoginUseCase {

    AuthResponseDtoUser execute(LoginRequestDtoUser dtoUser);
}
