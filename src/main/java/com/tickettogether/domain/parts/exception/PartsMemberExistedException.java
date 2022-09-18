package com.tickettogether.domain.parts.exception;

import com.tickettogether.global.error.ErrorCode;
import com.tickettogether.global.error.exception.BusinessException;

public class PartsMemberExistedException extends BusinessException {
    public PartsMemberExistedException() {
        super(ErrorCode.PARTS_MEMBER_EXISTED);
    }
}
