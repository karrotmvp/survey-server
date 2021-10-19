package com.daangn.survey.core.error.exception;

import com.daangn.survey.core.error.ErrorCode;

public class KarrotAuthenticationException extends BusinessException{
    private int code;

    public KarrotAuthenticationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public KarrotAuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
