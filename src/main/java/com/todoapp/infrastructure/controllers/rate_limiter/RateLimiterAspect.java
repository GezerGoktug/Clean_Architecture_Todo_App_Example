package com.todoapp.infrastructure.controllers.rate_limiter;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.todoapp.application.exceptions.BaseException;
import com.todoapp.application.exceptions.ErrorMessage;
import com.todoapp.application.exceptions.MessageType;
import com.todoapp.infrastructure.handlers.HttpContextHandler;

@Aspect
@Component
@Order(1)
public class RateLimiterAspect {

    @Autowired
    private HttpContextHandler httpContextHandler;

    @Autowired
    private RateLimiterService rateLimiterService;

    @Pointcut("@annotation(com.todoapp.infrastructure.controllers.rate_limiter.RateLimited)")
    public void rateLimitedMethods() {
    }

    @Before("rateLimitedMethods() && @annotation(rateLimited)") // metot çağrılmadan önce çalışır
    public void applyRateLimit(RateLimited rateLimited) {
        String clientIp = httpContextHandler.getRequest().getRemoteAddr();

        boolean allowed = rateLimiterService.isAllowed(clientIp, rateLimited.maxRequests(),
                rateLimited.timeWindowSeconds());

        if (!allowed) {
            throw new BaseException(
                    new ErrorMessage(("Please retry again later " + rateLimited.timeWindowSeconds() + "minutes"),
                            MessageType.TOO_MANY_REQUESTS),
                    429);
        }

    }
}
