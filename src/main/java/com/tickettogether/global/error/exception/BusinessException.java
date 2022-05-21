package com.tickettogether.global.error.exception;

import com.tickettogether.global.error.ErrorCode;

/**
 * 비니지스 로직을 수행하는 코드 흐름에서, 로직의 흐름을 진행할 수 없는 경우 발생하는 Exception
 */

public class BusinessException extends BaseException{
    public BusinessException(ErrorCode errorCode){
        super(errorCode);
    }

    public BusinessException(String message, ErrorCode errorCode){
        super(message, errorCode);
    }
}
