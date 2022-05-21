package com.tickettogether.global.error.exception;

import com.tickettogether.global.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthorityException extends BaseException{
    public AuthorityException(ErrorCode errorCode){
        super(errorCode);
    }

    public AuthorityException(String message, ErrorCode errorCode){
        super(message, errorCode);
    }
}
