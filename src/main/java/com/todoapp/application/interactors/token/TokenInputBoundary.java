package com.todoapp.application.interactors.token;

import java.security.Key;
import java.util.function.Function;


import io.jsonwebtoken.Claims;

public interface TokenInputBoundary {
    String generateAccessToken(String username, String email, String role, String id);

    String generateRefreshToken(String username, String email, String role, String id);

    Key getKey(String secretKey);

    Claims getClaims(String token, String secretKey);

    <T> T exportToken(String token, String secretKey, Function<Claims, T> claimsFunction);

    String getUsernameByToken(String token, String secretKey);

    Object getClaimsByKey(String token, String secretKey, String key);

    boolean isValidToken(String token, String secretKey);
}
