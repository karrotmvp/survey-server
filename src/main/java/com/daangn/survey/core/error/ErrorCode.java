package com.daangn.survey.core.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400,  " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405,  " Method Not Allowed"),
    ENTITY_NOT_FOUND(400, "Entity Not Found"),
    INTERNAL_SERVER_ERROR(500,  "Server Error"),
    INVALID_TYPE_VALUE(400,  " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "Access is Denied"),

    // Jwt
    UNSUPPORTED_JWT(401, "Unsupported Jwt"),
    EXPIRED_JWT(401, "Expired Jwt"),
    SIGNATURE_INVALID_JWT(401, "Signature Invalid Jwt"),
    JWT_NOT_FOUND(401, "Jwt Not Found")
    ;
    private final String message;
    private int status;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

}
