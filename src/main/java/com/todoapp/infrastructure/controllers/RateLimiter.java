package com.todoapp.infrastructure.controllers;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todoapp.application.exceptions.BaseException;
import com.todoapp.application.exceptions.ErrorMessage;
import com.todoapp.application.exceptions.MessageType;
import com.todoapp.infrastructure.config.RateLimiterService;
import com.todoapp.infrastructure.handlers.HttpContextHandler;

@Component
public class RateLimiter {

    @Autowired
    private HttpContextHandler httpContextHandler;

    @Autowired
    private RateLimiterService rateLimiterService;

    public <T> T limit(long maxRequests, long timeWindowSeconds, Supplier<T> action) {
        String clientIp = httpContextHandler.getRequest().getRemoteAddr();

        boolean allowed = rateLimiterService.isAllowed(clientIp, maxRequests, timeWindowSeconds);

        if (!allowed) {
            throw new BaseException(new ErrorMessage(("Please retry again later " + timeWindowSeconds + "minutes"),
                    MessageType.TOO_MANY_REQUESTS), 429);
        }

        return action.get();
    }
}
