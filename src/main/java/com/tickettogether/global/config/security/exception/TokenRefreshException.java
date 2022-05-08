package com.tickettogether.global.config.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TokenRefreshException extends RuntimeException{
    public TokenRefreshException(){
        super("Refresh token was expired. Please make a new login request");
    }

    public TokenRefreshException(String message){
        super(message);
    }

    public TokenRefreshException(String token, String message) {
        super(String.format("Failed for [%s]: %s", token, message));
    }
}
