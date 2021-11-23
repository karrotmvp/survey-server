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

    private Long memberId;

    private String daangnId;

    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String imageUrl;

    private String role;

    private long surveyCount;

    private boolean notifying;

    private String region;

    private LocalDateTime createdAt;
}
