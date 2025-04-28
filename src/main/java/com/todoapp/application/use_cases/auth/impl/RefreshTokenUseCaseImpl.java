package com.todoapp.application.use_cases.auth.impl;

import org.springframework.beans.factory.annotation.Value;

import com.todoapp.application.accessors.HttpContextAccessors;
import com.todoapp.application.dto.refreshToken.ResponseDtoRefreshToken;
import com.todoapp.application.exceptions.BaseException;
import com.todoapp.application.exceptions.ErrorMessage;
import com.todoapp.application.exceptions.MessageType;
import com.todoapp.application.interactors.token.TokenInputBoundary;
import com.todoapp.application.use_cases.auth.IRefreshTokenUseCase;

public class RefreshTokenUseCaseImpl implements IRefreshTokenUseCase {

    private final HttpContextAccessors httpContextAccessors;

    private final TokenInputBoundary tokenInteractor;

    @Value("${REFRESH_TOKEN_SECRET_KEY}")
    private String REFRESH_TOKEN_SECRET_KEY;

    public RefreshTokenUseCaseImpl(HttpContextAccessors httpContextAccessors, TokenInputBoundary tokenInteractor) {
        this.httpContextAccessors = httpContextAccessors;
        this.tokenInteractor = tokenInteractor;
    }

    private ResponseDtoRefreshToken toResponseDTO(String accessToken) {
        ResponseDtoRefreshToken responseDtoRefreshToken = new ResponseDtoRefreshToken();

        responseDtoRefreshToken.setAccessToken(accessToken);

        return responseDtoRefreshToken;
    }

    @Override
    public ResponseDtoRefreshToken execute() {
        String refreshToken = httpContextAccessors.getCookie("refreshToken");

        if (!tokenInteractor.isValidToken(refreshToken, REFRESH_TOKEN_SECRET_KEY)) {
            throw new BaseException(new ErrorMessage(refreshToken, MessageType.REFRESH_TOKEN_EXPIRED_DATE), 401);
        }

        String username = tokenInteractor.getUsernameByToken(refreshToken, REFRESH_TOKEN_SECRET_KEY);
        Object email = tokenInteractor.getClaimsByKey(refreshToken, REFRESH_TOKEN_SECRET_KEY, "email");
        Object userId = tokenInteractor.getClaimsByKey(refreshToken, REFRESH_TOKEN_SECRET_KEY, "userId");
        Object role = tokenInteractor.getClaimsByKey(refreshToken, REFRESH_TOKEN_SECRET_KEY, "role");

        String accessToken = tokenInteractor.generateAccessToken(username, email.toString(), role.toString(),
                userId.toString());
        String newRefreshToken = tokenInteractor.generateRefreshToken(username, email.toString(), role.toString(),
                userId.toString());

        httpContextAccessors.setCookie("refreshToken", newRefreshToken);

        return toResponseDTO(accessToken);
    }
}
