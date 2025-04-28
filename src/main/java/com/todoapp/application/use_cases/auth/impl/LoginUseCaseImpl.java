package com.todoapp.application.use_cases.auth.impl;

import com.todoapp.application.accessors.AuthenticationAccessors;
import com.todoapp.application.accessors.HttpContextAccessors;
import com.todoapp.application.ds_gateways.UserDsGateway;
import com.todoapp.application.ds_gateways.dto.UserDataResponseDTO;
import com.todoapp.application.dto.user.LoginRequestDtoUser;
import com.todoapp.application.dto.user.AuthResponseDtoUser;
import com.todoapp.application.exceptions.BaseException;
import com.todoapp.application.exceptions.ErrorMessage;
import com.todoapp.application.exceptions.MessageType;
import com.todoapp.application.interactors.token.TokenInputBoundary;
import com.todoapp.application.use_cases.auth.ILoginUseCase;
import com.todoapp.application.utils.ClassUtils;

public class LoginUseCaseImpl implements ILoginUseCase {

    private final UserDsGateway userDsGateway;

    private final TokenInputBoundary tokenInteractor;

    private final HttpContextAccessors httpContextAccessors;

    private final AuthenticationAccessors authenticationManager;

    public LoginUseCaseImpl(UserDsGateway userDsGateway,
            TokenInputBoundary tokenInteractor,
            HttpContextAccessors httpContextAccessors,
            AuthenticationAccessors authenticationTokenManager) {
        this.userDsGateway = userDsGateway;
        this.httpContextAccessors = httpContextAccessors;
        this.authenticationManager = authenticationTokenManager;
        this.tokenInteractor = tokenInteractor;
    }

    private AuthResponseDtoUser toResponseDTO(String accessToken, UserDataResponseDTO userDataResponseDTO) {
        AuthResponseDtoUser responseDtoUser = new AuthResponseDtoUser();

        ClassUtils.copyProperties(userDataResponseDTO, responseDtoUser);

        responseDtoUser.setAccessToken(accessToken);

        return responseDtoUser;

    }

    @Override
    public AuthResponseDtoUser execute(LoginRequestDtoUser dtoUser) {

        UserDataResponseDTO userDataResponseDTO = userDsGateway.getUserWithEmail(dtoUser.getEmail());

        if (userDataResponseDTO == null) {
            throw new BaseException(new ErrorMessage(dtoUser.getEmail(), MessageType.EMAIL_OR_PASSWORD_INVALID), 401);
        }

        try {

            authenticationManager.setAuthentication(dtoUser.getEmail(), dtoUser.getPassword());

            String accessToken = tokenInteractor.generateAccessToken(userDataResponseDTO.getUsername(),
                    userDataResponseDTO.getEmail(), userDataResponseDTO.getRole(),
                    userDataResponseDTO.getId().toString());

            String refreshToken = tokenInteractor.generateRefreshToken(userDataResponseDTO.getUsername(),
                    userDataResponseDTO.getEmail(), userDataResponseDTO.getRole(),
                    userDataResponseDTO.getId().toString());

            httpContextAccessors.setCookie("refreshToken", refreshToken);

            return toResponseDTO(accessToken, userDataResponseDTO);

        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(e.getMessage(), MessageType.EMAIL_OR_PASSWORD_INVALID), 401);
        }

    }
}
