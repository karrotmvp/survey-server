package com.daangn.survey.domain.survey.controller;

import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.core.annotation.CurrentUser;
import com.daangn.survey.domain.member.model.entity.BizMember;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.domain.survey.model.entity.Survey;
import com.daangn.survey.domain.survey.service.SurveyService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonReader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import static com.daangn.survey.common.message.ResponseMessage.*;

@Slf4j
@Tag(name = "설문 엔드포인트")
@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @Operation(summary = "설문 생성", description = "설문과 질문들을 저장합니다.")
    @PostMapping
    public ResponseEntity<ResponseDto<?>> saveSurvey(@CurrentUser Member member, @RequestBody Map<String, Object> responseBody){

        if(member == null) member = BizMember.builder().id(1L).daangnUserId("test").bizName("testBiz").imageUrl("test").nickname("test").phone("tse").build();

        Gson gson = new Gson();

        SurveyDto surveyDto = gson.fromJson(gson.toJson(responseBody), SurveyDto.class);

        surveyService.saveSurvey(member, surveyDto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, CREATE_SURVEY));
    }

    @Operation(summary = "설문 리스트 조회", description = "설문 리스트를 조회합니다.")
    @GetMapping
    public ResponseEntity<ResponseDto<List<SurveySummaryDto>>> getSurveys(@CurrentUser Member member){

        if(member == null) member = BizMember.builder().id(1L).daangnUserId("test").bizName("testBiz").imageUrl("test").nickname("test").phone("tse").build();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, READ_SURVEY_LIST, surveyService.findAll(member.getId())));
    }

    @Operation(summary = "설문 디테일 조회", description = "설문 디테일을 조회합니다.")
    @GetMapping("{surveyId}")
    public ResponseEntity<ResponseDto<?>> getSurveyDetail(@PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, READ_SURVEY_DETAIL, surveyService.findBySurveyId(surveyId)));
    }

    @Operation(summary = "설문 삭제", description = "설문을 삭제합니다.")
    @DeleteMapping("{surveyId}")
    public ResponseEntity<ResponseDto<?>> deleteSurvey(@CurrentUser Member member, @PathVariable Long surveyId){
        surveyService.deleteSurvey(surveyId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, DELETE_SURVEY));
    }
}
