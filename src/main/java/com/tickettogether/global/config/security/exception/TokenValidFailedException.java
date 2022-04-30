package com.tickettogether.global.config.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenValidFailedException extends RuntimeException{
    public TokenValidFailedException() {
        super("Failed to validate token.");
    }

    public TokenValidFailedException(String message) {
        super(message);
    }
}
