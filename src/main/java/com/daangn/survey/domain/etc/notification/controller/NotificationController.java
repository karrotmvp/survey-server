package com.daangn.survey.domain.etc.notification.controller;

import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.core.annotation.CurrentUser;
import com.daangn.survey.domain.etc.notification.model.dto.NotificationDto;
import com.daangn.survey.domain.etc.notification.model.mapper.NotificationMapper;
import com.daangn.survey.domain.etc.notification.service.NotificationService;
import com.daangn.survey.domain.member.model.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.daangn.survey.common.message.ResponseMessage.CREATE_NOTIFICATION;

@Slf4j
@Tag(name = "알림 엔드포인트")
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @Operation(summary = "채팅 알림 신청", description = "채팅 알림을 신청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "채팅 알림 신청 성공", content = @Content)})
    @PostMapping("/chat")
    public ResponseEntity<ResponseDto<?>> saveChatNotification(@Parameter(description = "Member", hidden = true) @CurrentUser Member member,
                                                       @Parameter(description = "Notification", schema = @Schema(implementation = NotificationDto.class)) @RequestBody NotificationDto notificationDto){

        notificationService.saveNotification(notificationMapper.toEntity(notificationDto, member, "chat"));

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(HttpStatus.CREATED, CREATE_NOTIFICATION));
    }
}