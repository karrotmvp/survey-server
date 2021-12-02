package com.daangn.survey.domain.survey.model.dto;

import com.daangn.survey.domain.member.model.dto.BizProfileDto;
import com.daangn.survey.domain.member.model.entity.BizProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SurveyBriefDto {
    @Schema(description = "설문 ID")
    private Long surveyId;

    @Schema(description = "예상 시간")
    private int estimatedTime;

    @Schema(description = "질문 수")
    private int questionCount;

    @Schema(description = "설문 제목", required = true)
    private String title;

    @Schema(description = "설명")
    private String description;

    @Schema(description = "비즈프로필 정보")
    private BizProfileDto bizProfile;

    @Schema(description = "설문 대상", required = true)
    private String target;

    @Schema(description = "설문 생성일", required = true)
    private LocalDateTime createdAt;

    @JsonIgnore
    public boolean isCoverImageNull(){
        return this.bizProfile.getCoverImageUrls().size() == 0;
    }

    public void setCoverImageUrls(List<String> coverImageUrls){
        this.getBizProfile().setCoverImageUrls(coverImageUrls);
    }
}
