package com.daangn.survey.admin.dto;

import com.daangn.survey.domain.deprecated.survey.survey.model.entity.Target;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminSurveyDto {
    private Long id;

    private String writer;

    private Long memberId;

    private String title;

    private long responseCount;

    private int target;

    private String korTarget;

    private LocalDateTime publishedAt;

    private LocalDateTime createdAt;

    public void resolveKorTarget(){
        this.korTarget = Target.findValue(target);
    }
}
