package com.daangn.survey.admin.dto;

import com.daangn.survey.domain.survey.survey.model.entity.Target;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminSurveyDto {
    private long surveyId;
    private String writer;
    private String title;
    private long responseCount;
    private int target;
    private String korTarget;
    private LocalDateTime publishedAt;

    public void resolveKorTarget(){
        this.korTarget = Target.findValue(target);
    }
}
