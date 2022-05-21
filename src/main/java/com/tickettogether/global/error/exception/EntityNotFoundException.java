package com.tickettogether.global.error.exception;

import com.tickettogether.global.error.ErrorCode;

/**
 * 각 엔티티들을 못찾았을 경우 발생하는 Exception
 */
public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException(ErrorCode errorCode){
        super(errorCode);
    }
}
