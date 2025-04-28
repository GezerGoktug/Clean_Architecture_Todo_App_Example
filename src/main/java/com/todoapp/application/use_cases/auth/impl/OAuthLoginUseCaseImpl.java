package com.todoapp.application.use_cases.auth.impl;

import java.util.Map;

import com.todoapp.application.ds_gateways.UserDsGateway;
import com.todoapp.application.ds_gateways.dto.UserDataResponseDTO;
import com.todoapp.application.dto.user.LoginRequestDtoUser;
import com.todoapp.application.dto.user.RegisterRequestDtoUser;
import com.todoapp.application.dto.user.AuthResponseDtoUser;
import com.todoapp.application.use_cases.auth.ILoginUseCase;
import com.todoapp.application.use_cases.auth.IOAuthLoginUseCase;
import com.todoapp.application.use_cases.auth.IRegisterUserUseCase;

public class OAuthLoginUseCaseImpl implements IOAuthLoginUseCase {

    private final UserDsGateway userDsGateway;

    private final IRegisterUserUseCase registerUserUseCase;

    private final ILoginUseCase loginUseCase;

    public OAuthLoginUseCaseImpl(UserDsGateway userDsGateway,
            IRegisterUserUseCase registerUserUseCase,
            ILoginUseCase loginUseCase) {
        this.userDsGateway = userDsGateway;
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
    }

    @Override
    public String execute(Map<String, Object> userAttributes) {
        String name = (String) userAttributes.get("name");
        String email = (String) userAttributes.get("email");

        UserDataResponseDTO userDataResponseDTO = userDsGateway.getUserWithEmail(email);

        if (userDataResponseDTO == null) {
            RegisterRequestDtoUser registerRequestDtoUser = new RegisterRequestDtoUser();

            registerRequestDtoUser.setEmail(email);
            registerRequestDtoUser.setUsername(name);
            registerRequestDtoUser.setPassword(null);

            AuthResponseDtoUser responseDtoUser = registerUserUseCase.execute(registerRequestDtoUser, true);

            return responseDtoUser.getAccessToken();

        } else {
            LoginRequestDtoUser loginRequestDtoUser = new LoginRequestDtoUser();

            loginRequestDtoUser.setEmail(email);
            loginRequestDtoUser.setPassword(null);

            AuthResponseDtoUser responseDtoUser = loginUseCase.execute(loginRequestDtoUser);

            return responseDtoUser.getAccessToken();

        }
    }

}
