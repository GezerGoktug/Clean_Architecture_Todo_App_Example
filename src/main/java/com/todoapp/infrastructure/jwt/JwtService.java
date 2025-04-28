package com.todoapp.infrastructure.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.todoapp.application.interactors.token.TokenInputBoundary;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService implements TokenInputBoundary {

    @Value("${ACCESS_TOKEN_SECRET_KEY}")
    private String ACCESS_TOKEN_SECRET_KEY;

    @Value("${REFRESH_TOKEN_SECRET_KEY}")
    private String REFRESH_TOKEN_SECRET_KEY;

    private String generateToken(String username, String email, String id, String role, String secretKey,
            Date expireTime) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", email);
        claims.put("userId", id);
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setSubject(username)
                .setExpiration(expireTime)
                .signWith(getKey(secretKey), SignatureAlgorithm.HS256).compact();
    }

    public String generateAccessToken(String username, String email, String role, String id) {
        return generateToken(username, email, id, role, ACCESS_TOKEN_SECRET_KEY,
                new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2));
    }

    public String generateRefreshToken(String username, String email, String role, String id) {
        return generateToken(username, email, id, role, REFRESH_TOKEN_SECRET_KEY,
                new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15));
    }

    public Key getKey(String secretKey) {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public Claims getClaims(String token, String secretKey) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getKey(secretKey)).build().parseClaimsJws(token).getBody();
        return claims;
    }

    public <T> T exportToken(String token, String secretKey, Function<Claims, T> claimsFunction) {
        Claims claims = getClaims(token, secretKey);
        return claimsFunction.apply(claims);
    }

    public String getUsernameByToken(String token, String secretKey) {
        return exportToken(token, secretKey, Claims::getSubject);
    }

    public Object getClaimsByKey(String token, String secretKey, String key) {
        Claims claims = getClaims(token, secretKey);
        return claims.get(key);
    }

    public boolean isValidToken(String token, String secretKey) {
        Date expiredDate = exportToken(token, secretKey, Claims::getExpiration);
        return new Date().before(expiredDate);
    }
}
