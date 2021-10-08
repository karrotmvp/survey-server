package com.daangn.survey.survey.controller;

import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.survey.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.daangn.survey.common.message.ResponseMessage.SURVEY_SAVED;

@Tag(name = "설문 엔드포인트")
@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @Operation(summary = "설문 생성", description = "description")
    @PostMapping
    public ResponseEntity<ResponseDto<?>> saveSurvey(){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, SURVEY_SAVED));
    }

    @Operation(summary = "리스트 조회", description = "description")
    @GetMapping
    public ResponseEntity<ResponseDto<?>> getSurveys(){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, SURVEY_SAVED));
    }

    @Operation(summary = "설문 디테일 조회", description = "description")
    @GetMapping("{surveyId}")
    public ResponseEntity<ResponseDto<?>> getSurveyDetail(){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, SURVEY_SAVED));
    }

    @Operation(summary = "설문 삭제", description = "description")
    @DeleteMapping("{surveyId}")
    public ResponseEntity<ResponseDto<?>> deleteSurvey(){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, SURVEY_SAVED));
    }
}
