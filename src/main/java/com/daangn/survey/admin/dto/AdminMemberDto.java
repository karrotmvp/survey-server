package com.daangn.survey.admin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminMemberDto {
    @Schema(description = "멤버 ID")
    private String memberId;

    @Schema(description = "당근 ID")
    private String daangnId;

    @Schema(description = "당근 이름")
    private String name;

    @Schema(description = "프로필 URL")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String imageUrl;

    @Schema(description = "역할")
    private String role;

    @Schema(description = "설문 수")
    private int surveyCount;

    @Schema(description = "가입일")
    private LocalDateTime createdAt;
}
