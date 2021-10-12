package com.daangn.survey.domain.question.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    @Schema(description = "질문 ID", readOnly = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long questionId;

    @Schema(description = "질문 타입", required = true, allowableValues = {"1", "2"})
    private Long questionType;

    @Schema(description = "질문 내용", required = true)
    private String text;

    @Schema(description = "선택지", required = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ChoiceDto> choices;
}
