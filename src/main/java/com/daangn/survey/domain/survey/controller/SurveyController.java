package com.daangn.survey.domain.survey.controller;

import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.service.SurveyService;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.daangn.survey.common.message.ResponseMessage.SURVEY_SAVED;

@Slf4j
@Tag(name = "설문 엔드포인트")
@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @Operation(summary = "설문 생성", description = "description")
    @PostMapping
    public ResponseEntity<ResponseDto<?>> saveSurvey(@RequestBody Map<String, Object> responseBody){

        Gson gson = new Gson();

        SurveyDto survey = gson.fromJson(responseBody.toString(), SurveyDto.class);

        surveyService.saveSurvey(survey);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, SURVEY_SAVED));
    }

    @Operation(summary = "리스트 조회", description = "description")
    @GetMapping
    public ResponseEntity<ResponseDto<?>> getSurveys(){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, SURVEY_SAVED));
    }

    @Operation(summary = "설문 디테일 조회", description = "description")
    @GetMapping("{surveyId}")
    public ResponseEntity<ResponseDto<?>> getSurveyDetail(@PathVariable Long surveyId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, SURVEY_SAVED));
    }

    @Operation(summary = "설문 삭제", description = "description")
    @DeleteMapping("{surveyId}")
    public ResponseEntity<ResponseDto<?>> deleteSurvey(){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, SURVEY_SAVED));
    }
}
