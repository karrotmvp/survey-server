package com.daangn.survey.domain.survey.question.model.dto;

import com.daangn.survey.domain.survey.question.model.entity.QuestionType;
import com.daangn.survey.domain.survey.question.model.entity.QuestionTypeCode;
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

    @Schema(description = "질문 타입", required = true, allowableValues = {"1", "2", "3"})
    private Long questionType;

    @Schema(description = "질문 내용", required = true)
    private String text;

    @Schema(description = "질문에 대한 설명",
            example = "example description")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;

    @Schema(description = "선택지")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ChoiceDto> choices;

    public boolean checkQuestionTypeCondition(){
        if(getQuestionType() == QuestionTypeCode.CHOICE_QUESTION.getNumber())
            return choices != null;
        else
            return choices == null;
    }

    public QuestionType convertToQuestionType(){
        QuestionTypeCode code = QuestionTypeCode.findByNumber(getQuestionType());
        return QuestionType.builder().id(code.getNumber()).name(code.getKorean()).build();
    }
}
