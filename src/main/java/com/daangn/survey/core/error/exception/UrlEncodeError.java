package com.daangn.survey.core.error.exception;

import com.daangn.survey.core.error.ErrorCode;

public class UrlEncodeError extends BusinessException{

    public UrlEncodeError(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public UrlEncodeError(ErrorCode errorCode) {
        super(errorCode);
    }
}
