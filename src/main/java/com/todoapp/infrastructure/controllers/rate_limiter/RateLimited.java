package com.todoapp.infrastructure.controllers.rate_limiter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // Sadece methodlar üzerinde kullan
@Retention(RetentionPolicy.RUNTIME) // Runtimeda kullanılcak
public @interface RateLimited {

    long maxRequests() default 100;

    long timeWindowSeconds() default 60;

}
