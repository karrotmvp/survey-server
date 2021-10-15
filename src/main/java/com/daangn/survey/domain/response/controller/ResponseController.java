package com.daangn.survey.domain.response.controller;

import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.core.annotation.CurrentUser;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.response.model.dto.SurveyResponseDto;
import com.daangn.survey.domain.response.service.ResponseService;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
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

import static com.daangn.survey.common.message.ResponseMessage.CREATE_SURVEY;

@Slf4j
@Tag(name = "답변 엔드포인트")
@RestController
@RequestMapping("/api/v1/responses")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;


    @Operation(summary = "답변 저장", description = "답변을 저장합니다.")
    @PostMapping
    public ResponseEntity<ResponseDto<?>> saveResponse(@CurrentUser Member member, @RequestBody Map<String, Object> responseBody){

        if(member == null) member = Member.builder().id(1L).daangnId("test").name("testBiz").imageUrl("test").build();

        Gson gson = new Gson();

        SurveyResponseDto surveyResponseDto = gson.fromJson(gson.toJson(responseBody), SurveyResponseDto.class);

        responseService.saveResponse(member, surveyResponseDto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, CREATE_SURVEY));
    }
}
