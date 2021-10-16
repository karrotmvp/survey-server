package com.daangn.survey.core.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400,"" , " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405,  ""," Method Not Allowed"),
    ENTITY_NOT_FOUND(404, "","Entity Not Found"),
    INTERNAL_SERVER_ERROR(500,  "","Server Error"),
    INVALID_TYPE_VALUE(400,  ""," Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "","Access is Denied"),

    // Jwt
    UNSUPPORTED_JWT(401, "","Unsupported Jwt"),
    EXPIRED_JWT(401, "","Expired Jwt"),
    SIGNATURE_INVALID_JWT(401, "","Signature Invalid Jwt"),
    JWT_NOT_FOUND(401, "","Jwt Not Found"),

    // Survey
    SURVEY_NOT_FOUND(404, "","Survey Not Found"),
    SURVEY_ALREADY_DELETED(401, "","Survey Already Deleted"),
    NOT_AUTHORIZED_FOR_DELETE(401, "","Member Not Authorized For Delete"),

    // Question
    QUESTION_TYPE_CONDITION_NOT_MATCHED(400, "","Question Type Condition Not Matched"),
    QUESTION_NOT_FOUND(404, "","Question Not Found"),
    QUESTION_TYPE_NOT_MATCHED(400, "","Question Type Not Matched"),

    // Choice
    CHOICE_NOT_FOUND(404, "","Choice Not Found"),

    // Member
    MEMBER_NOT_FOUND(404, "","Member Not Found"),

    // Karrot
    KARROT_FORBIDDEN(403, "","Karrot Forbidden"),
    KARROT_BAD_REQUEST(400, "","Karrot Bad Request"),
    KARROT_PROFILE_NOT_FOUND(404, "","Karrot Profile Not Found")
    ;
    private final String message;
    private final String code;
    private int status;

    ErrorCode(final int status,final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
