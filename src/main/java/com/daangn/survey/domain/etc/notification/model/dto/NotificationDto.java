package com.daangn.survey.domain.etc.notification.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    @Schema(description = "알림 주제", required = true)
    private String subject;

    @Schema(description = "알림 선택")
    private boolean isNotifying;
}

