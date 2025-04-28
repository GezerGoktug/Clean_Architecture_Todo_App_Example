package com.todoapp.application.use_cases.auth.impl;

import java.util.HashMap;

import com.todoapp.application.accessors.AuthenticationAccessors;
import com.todoapp.application.accessors.HttpContextAccessors;
import com.todoapp.application.ds_gateways.UserDsGateway;
import com.todoapp.application.ds_gateways.dto.UserDataRequestDTO;
import com.todoapp.application.ds_gateways.dto.UserDataResponseDTO;
import com.todoapp.application.dto.user.RegisterRequestDtoUser;
import com.todoapp.application.dto.user.AuthResponseDtoUser;
import com.todoapp.application.exceptions.BaseException;
import com.todoapp.application.exceptions.ErrorMessage;
import com.todoapp.application.exceptions.MessageType;
import com.todoapp.application.exceptions.NotValidException;
import com.todoapp.application.interactors.token.TokenInputBoundary;
import com.todoapp.application.use_cases.auth.IRegisterUserUseCase;
import com.todoapp.application.utils.ClassUtils;
import com.todoapp.domain.entities.user.User;
import com.todoapp.domain.enums.Role;
import com.todoapp.domain.factory.UserFactory.IUserFactory;

public class RegisterUserUseCaseImpl implements IRegisterUserUseCase {

    private final UserDsGateway userDsGateway;

    private final IUserFactory userFactory;

    private final TokenInputBoundary tokenInteractor;

    private final AuthenticationAccessors authenticationManager;

    private final HttpContextAccessors httpContextAccessors;

    public RegisterUserUseCaseImpl(UserDsGateway userDsGateway, IUserFactory userFactory,
            TokenInputBoundary tokenInteractor,
            AuthenticationAccessors authenticationManager, HttpContextAccessors httpContextAccessors) {
        this.userDsGateway = userDsGateway;
        this.userFactory = userFactory;
        this.tokenInteractor = tokenInteractor;
        this.authenticationManager = authenticationManager;
        this.httpContextAccessors = httpContextAccessors;
    }

    private UserDataRequestDTO convertDataRequestDTO(User user) {
        UserDataRequestDTO userDataRequestDTO = new UserDataRequestDTO();

        ClassUtils.copyProperties(user, userDataRequestDTO);

        userDataRequestDTO.setRole(user.getRole().name());

        return userDataRequestDTO;
    }

    private AuthResponseDtoUser toResponseDTO(String accessToken, UserDataResponseDTO userDataResponseDTO) {
        AuthResponseDtoUser responseDtoUser = new AuthResponseDtoUser();

        ClassUtils.copyProperties(userDataResponseDTO, responseDtoUser);

        responseDtoUser.setAccessToken(accessToken);

        return responseDtoUser;

    }

    @Override
    public AuthResponseDtoUser execute(RegisterRequestDtoUser dtoUser, boolean isOauthLogin) {

        boolean isExistUserByEmail = userDsGateway.existUserByEmail(dtoUser.getEmail());

        if (isExistUserByEmail) {
            throw new BaseException(new ErrorMessage(dtoUser.getEmail(), MessageType.EMAIL_ALREADY_USED), 404);
        }

        User newUser = userFactory.create(dtoUser.getUsername(), dtoUser.getEmail(),
                dtoUser.getPassword(), Role.USER);

        if (!isOauthLogin) {
            HashMap<String, Object> validated = newUser.validateUser();

            if (!validated.isEmpty())
                throw new NotValidException(validated, 400);

            newUser.setPassword(authenticationManager.hashPassword(newUser.getPassword()));
        }

        UserDataResponseDTO userDataResponseDTO = userDsGateway.saveUser(convertDataRequestDTO(newUser));

        String accessToken = tokenInteractor.generateAccessToken(dtoUser.getUsername(), userDataResponseDTO.getEmail(),
                userDataResponseDTO.getRole(), userDataResponseDTO.getId().toString());

        String refreshToken = tokenInteractor.generateRefreshToken(dtoUser.getUsername(),
                userDataResponseDTO.getEmail(),
                userDataResponseDTO.getRole(), userDataResponseDTO.getId().toString());

        httpContextAccessors.setCookie("refreshToken", refreshToken);

        return toResponseDTO(accessToken, userDataResponseDTO);

    }
}
