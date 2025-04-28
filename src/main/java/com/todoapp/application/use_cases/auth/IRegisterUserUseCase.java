package com.todoapp.application.use_cases.auth;

import com.todoapp.application.dto.user.RegisterRequestDtoUser;
import com.todoapp.application.dto.user.AuthResponseDtoUser;

public interface IRegisterUserUseCase {

    AuthResponseDtoUser execute(RegisterRequestDtoUser dtoUser,boolean isOauthLogin);

}
