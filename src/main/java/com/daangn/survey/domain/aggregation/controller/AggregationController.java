package com.daangn.survey.domain.aggregation.controller;

import com.daangn.survey.admin.service.AdminService;
import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.common.message.ResponseMessage;
import com.daangn.survey.core.annotation.CurrentUser;
import com.daangn.survey.domain.aggregation.model.*;
import com.daangn.survey.domain.aggregation.model.individual.SurveyResponsesBrief;
import com.daangn.survey.domain.aggregation.service.AggregationService;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.response.service.ResponseService;
import com.daangn.survey.domain.survey.model.dto.SurveySummaryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.daangn.survey.common.message.ResponseMessage.READ_SURVEY_LIST;

@RequiredArgsConstructor
@RequestMapping("/aggregation")
@RestController
public class AggregationController {

    private final AdminService adminService;
    private final ResponseService responseService;
    private final AggregationService aggregationService;

    @Operation(summary = "답변 통계 조회", description = "설문 결과를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "답변 결과 조회 성공",
                    content = @Content(schema = @Schema(implementation = SurveyAggregation.class))),
            @ApiResponse(responseCode = "401", description = "답변 결과 조회 실패 (권한 에러)", content = @Content)
    })
    @GetMapping("/{surveyId}")
    public ResponseEntity<ResponseDto<SurveyAggregation>> getAggregation(@CurrentUser Member member, @PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_AGGREGATION, aggregationService.getSurveyAggregations(surveyId)));

    }

    @GetMapping("/{surveyId}/responses/brief")
    public ResponseEntity<ResponseDto<SurveyResponsesBrief>> getResponsesBrief(@PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_RESPONSES_BRIEF, aggregationService.getSurveyResponsesBrief(surveyId)));
    }

    @GetMapping("/individual/{responseId}")
    public ResponseEntity<ResponseDto<?>> getIndividualResponse(@PathVariable Long responseId){
       return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE, aggregationService.getIndividualSurveyResponse(responseService.getSurveyResponse(responseId))));
    }
}
