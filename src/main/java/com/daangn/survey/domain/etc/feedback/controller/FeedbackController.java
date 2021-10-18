package com.daangn.survey.domain.etc.feedback.controller;

import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.core.annotation.CurrentUser;
import com.daangn.survey.domain.etc.feedback.model.dto.FeedbackDto;
import com.daangn.survey.domain.etc.feedback.model.mapper.FeedbackMapper;
import com.daangn.survey.domain.etc.feedback.service.FeedbackService;
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

import static com.daangn.survey.common.message.ResponseMessage.CREATE_FEEDBACK;

@Slf4j
@Tag(name = "피드백 엔드포인트")
@RestController
@RequestMapping("/api/v1/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;

    @Operation(summary = "피드백 생성", description = "피드백을 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "피드백 저장 성공", content = @Content)})
    @PostMapping
    public ResponseEntity<ResponseDto<?>> saveFeedback(@Parameter(description = "Member", hidden = true) @CurrentUser Member member,
                                                       @Parameter(description = "Feedback", schema = @Schema(implementation = FeedbackDto.class)) @RequestBody FeedbackDto feedbackDto){

        feedbackService.saveFeedback(feedbackMapper.toEntity(feedbackDto, member));

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(HttpStatus.CREATED, CREATE_FEEDBACK));
    }
}
