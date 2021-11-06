package com.daangn.survey.core.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400,"C001" , " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405,  "C002"," Method Not Allowed"),
    ENTITY_NOT_FOUND(404, "C003","Entity Not Found"),
    INTERNAL_SERVER_ERROR(500,  "C004","Server Error"),
    INVALID_TYPE_VALUE(400,  "C005"," Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006","Access is Denied"),

    // Jwt
    UNSUPPORTED_JWT(401, "J001","Unsupported Jwt"),
    EXPIRED_JWT(401, "J002","Expired Jwt"),
    SIGNATURE_INVALID_JWT(401, "J003","Signature Invalid Jwt"),
    JWT_NOT_FOUND(401, "J004","Jwt Not Found"),

    // Survey
    SURVEY_NOT_FOUND(404, "S001","Survey Not Found"),
    SURVEY_ALREADY_DELETED(401, "S002","Survey Already Deleted"),
    NOT_AUTHORIZED_FOR_DELETE(401, "S003","Member Not Authorized For Delete"),

    // Question
    QUESTION_TYPE_CONDITION_NOT_MATCHED(400, "Q001","Question Type Condition Not Matched"),
    QUESTION_NOT_FOUND(404, "Q002","Question Not Found"),
    QUESTION_TYPE_NOT_MATCHED(400, "Q003","Question Type Not Matched"),

    // Response
    RESPONSE_NOT_FOUND(404, "R001", "Response Not Found"),

    // Choice
    CHOICE_NOT_FOUND(404, "C101","Choice Not Found"),

    // Member
    MEMBER_NOT_FOUND(404, "M001","Member Not Found"),

    // Karrot
    KARROT_FORBIDDEN(403, "K001","Karrot Forbidden"),
    KARROT_BAD_REQUEST(400, "K002","Karrot Bad Request"),
    KARROT_PROFILE_NOT_FOUND(404, "K003","Karrot Profile Not Found")
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
