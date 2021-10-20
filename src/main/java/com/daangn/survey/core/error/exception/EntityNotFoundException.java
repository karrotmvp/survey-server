package com.daangn.survey.core.error.exception;

import com.daangn.survey.core.error.ErrorCode;

public class EntityNotFoundException extends BusinessException{

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
