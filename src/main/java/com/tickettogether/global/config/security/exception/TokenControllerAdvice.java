package com.tickettogether.global.config.security.exception;

import com.tickettogether.global.exception.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenControllerAdvice {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected BaseResponse<String> handleTokenRefreshException(TokenRefreshException ex) {
        return new BaseResponse<>(ex, 2010);
    }

    @ExceptionHandler(value = TokenValidFailedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected BaseResponse<String> handleTokenValidException(TokenValidFailedException ex){
        return new BaseResponse<>(ex, 2011);
    }
}
