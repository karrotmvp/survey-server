package com.daangn.survey.admin.dto;

import com.daangn.survey.domain.etc.notification.repository.NotificationRepository;
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

    @Schema(description = "이미지 URL")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String imageUrl;

    @Schema(description = "역할")
    private String role;

    @Schema(description = "설문 수")
    private int surveyCount;

    @Schema(description = "채팅 받기 여부")
    private boolean notifying;

    @Schema(description = "지역")
    private String region;

    @Schema(description = "가입일")
    private LocalDateTime createdAt;
}
