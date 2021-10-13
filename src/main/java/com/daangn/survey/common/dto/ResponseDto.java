package com.daangn.survey.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Getter
public class ResponseDto<T>{
    @Schema(description = "응답상태")
    private final int status;
    @Schema(description = "응답 메시지")
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(description = "데이터")
    private final T data;
    @Schema(description = "타임스탬프")
    private final long timestamp;

    public ResponseDto(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResponseDto<T> of(HttpStatus httpStatus, String message) {
        int status = Optional.ofNullable(httpStatus)
                .orElse(HttpStatus.OK)
                .value();
        return new ResponseDto<>(status, message, null);
    }

    public static <T> ResponseDto<T> of(HttpStatus httpStatus, String message, T data) {
        int status = Optional.ofNullable(httpStatus)
                .orElse(HttpStatus.OK)
                .value();
        return new ResponseDto<>(status, message, data);
    }
}