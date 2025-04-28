package com.todoapp.infrastructure.controllers;

import com.todoapp.application.dto.base.ResponseDtoDefault;
import com.todoapp.application.dto.refreshToken.ResponseDtoRefreshToken;
import com.todoapp.application.dto.user.AuthResponseDtoUser;
import com.todoapp.infrastructure.dto.auth.AuthControllerLoginRequest;
import com.todoapp.infrastructure.dto.auth.AuthControllerRegisterRequest;

public interface IAuthController {

    RootEntity<AuthResponseDtoUser> register(AuthControllerRegisterRequest request);

    RootEntity<ResponseDtoRefreshToken> refreshToken();

    RootEntity<ResponseDtoDefault> logout();

    RootEntity<AuthResponseDtoUser> login(AuthControllerLoginRequest request);
}
