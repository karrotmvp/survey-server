package com.daangn.survey.admin.dto;

import com.daangn.survey.domain.response.model.entity.Answerable;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDetailDto {
    private QuestionResponseDto question;
    private Answerable response;
}
