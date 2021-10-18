package com.daangn.survey.domain.etc.feedback.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {

    @Schema(description = "피드백 질문", required = true)
    private String question;

    @Schema(description = "피드백 답변")
    private String answer;
}
