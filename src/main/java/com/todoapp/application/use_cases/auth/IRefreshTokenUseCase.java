package com.todoapp.application.use_cases.auth;

import com.todoapp.application.dto.refreshToken.ResponseDtoRefreshToken;

public interface IRefreshTokenUseCase {
    
    ResponseDtoRefreshToken execute();
}
