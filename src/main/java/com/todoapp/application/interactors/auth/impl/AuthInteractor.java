package com.todoapp.application.interactors.auth.impl;

import java.util.Map;

import com.todoapp.application.dto.base.ResponseDtoDefault;
import com.todoapp.application.dto.refreshToken.ResponseDtoRefreshToken;
import com.todoapp.application.dto.user.LoginRequestDtoUser;
import com.todoapp.application.dto.user.RegisterRequestDtoUser;
import com.todoapp.application.interactors.auth.AuthInputBoundary;
import com.todoapp.application.dto.user.AuthResponseDtoUser;
import com.todoapp.application.use_cases.auth.ILoginUseCase;
import com.todoapp.application.use_cases.auth.ILogoutUseCase;
import com.todoapp.application.use_cases.auth.IOAuthLoginUseCase;
import com.todoapp.application.use_cases.auth.IRefreshTokenUseCase;
import com.todoapp.application.use_cases.auth.IRegisterUserUseCase;

public class AuthInteractor implements AuthInputBoundary {

    private final IRegisterUserUseCase registerUserUseCase;

    private final IRefreshTokenUseCase refreshTokenUseCase;

    private final ILogoutUseCase logoutUseCase;

    private final ILoginUseCase loginUseCase;

    private final IOAuthLoginUseCase oAuthLoginUseCase;

    public AuthInteractor(IRegisterUserUseCase registerUserUseCase, IRefreshTokenUseCase refreshTokenUseCase,
            ILogoutUseCase logoutUseCase, ILoginUseCase loginUseCase, IOAuthLoginUseCase oAuthLoginUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.logoutUseCase = logoutUseCase;
        this.loginUseCase = loginUseCase;
        this.oAuthLoginUseCase = oAuthLoginUseCase;
    }

    @Override
    public AuthResponseDtoUser register(RegisterRequestDtoUser dtoUser) {
        return registerUserUseCase.execute(dtoUser, false);
    }

    @Override
    public ResponseDtoRefreshToken refreshToken() {
        return refreshTokenUseCase.execute();
    }

    @Override
    public ResponseDtoDefault logout() {
        return logoutUseCase.execute();
    }

    @Override
    public AuthResponseDtoUser login(LoginRequestDtoUser dtoUser) {
        return loginUseCase.execute(dtoUser);
    }

    @Override
    public String oauth2Login(Map<String, Object> userAttributes) {
        return oAuthLoginUseCase.execute(userAttributes);
    }

}