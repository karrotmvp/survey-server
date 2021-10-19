package com.daangn.survey.domain.question.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceDto {
    @Schema(description = "선택지 ID", readOnly = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long choiceId;

    @Schema(description = "선택지 값", required = true)
    private String value;
}
