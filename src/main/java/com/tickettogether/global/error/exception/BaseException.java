package com.tickettogether.global.error.exception;

import com.tickettogether.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BaseException extends RuntimeException{
    private final ErrorCode errorCode;

    public BaseException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
