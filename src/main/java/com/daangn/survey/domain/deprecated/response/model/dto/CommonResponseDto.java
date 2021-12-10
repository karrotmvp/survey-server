package com.daangn.survey.domain.deprecated.response.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseDto {

    private int questionType;

    private Long questionId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String answer;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long choiceId;
}
