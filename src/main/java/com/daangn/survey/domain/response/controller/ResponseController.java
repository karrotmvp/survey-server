package com.daangn.survey.domain.response.controller;

import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.common.message.ResponseMessage;
import com.daangn.survey.core.annotation.CurrentUser;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.response.model.dto.SurveyResponseDto;
import com.daangn.survey.domain.response.service.ResponseService;
import com.google.gson.Gson;
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

import java.util.Map;

@Slf4j
@Tag(name = "답변 엔드포인트")
@RestController
@RequestMapping("/api/v1/responses")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;

    @Operation(summary = "답변 저장", description = "답변을 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "답변 저장 성공", content = @Content)})
    @PostMapping
    public ResponseEntity<ResponseDto<?>> saveResponse(@Parameter(description = "Member", hidden = true) @CurrentUser Member member,
                @Parameter(description = "requestBody", schema = @Schema(implementation = SurveyResponseDto.class)) @RequestBody Map<String, Object> requestBody){

        if(member == null) member = Member.builder().id(1L).daangnId("test").name("testBiz").imageUrl("test").build();

        Gson gson = new Gson();

        SurveyResponseDto surveyResponseDto = gson.fromJson(gson.toJson(requestBody), SurveyResponseDto.class);

        responseService.saveResponse(member, surveyResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(HttpStatus.OK, ResponseMessage.CREATE_RESPONSE));
    }
}
