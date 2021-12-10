package com.daangn.survey.domain.deprecated.aggregation.model.individual;

import com.daangn.survey.domain.deprecated.response.model.entity.Answerable;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndividualResponseDetailDto {
    private QuestionResponseDto question;
    private Answerable response;
}
