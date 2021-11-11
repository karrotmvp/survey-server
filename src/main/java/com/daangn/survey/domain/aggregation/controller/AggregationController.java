package com.daangn.survey.domain.aggregation.controller;

import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.common.message.ResponseMessage;
import com.daangn.survey.domain.aggregation.model.*;
import com.daangn.survey.domain.aggregation.repository.AggregateRepository;
import com.daangn.survey.domain.aggregation.service.AggregationService;
import com.daangn.survey.domain.question.model.entity.QuestionTypeCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/aggregation")
@RestController
public class AggregationController {

    private final AggregationService aggregationService;

    @GetMapping("/{surveyId}")
    public ResponseEntity<ResponseDto<SurveyAggregation>> getAggregation(@PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_AGGREGATION, aggregationService.getSurveyAggregations(surveyId)));

    }
}
