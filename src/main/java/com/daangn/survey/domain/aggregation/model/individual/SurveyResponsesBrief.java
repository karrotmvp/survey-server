package com.daangn.survey.domain.aggregation.model.individual;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponsesBrief {
    private int count;
    private List<Long> responseIds;
}
