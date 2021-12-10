package com.daangn.survey.domain.deprecated.aggregation;

import com.daangn.survey.common.message.ResponseMessage;
import com.daangn.survey.common.model.ResponseDto;
import com.daangn.survey.core.annotation.CurrentUser;
import com.daangn.survey.domain.deprecated.aggregation.model.SurveyAggregation;
import com.daangn.survey.domain.deprecated.aggregation.model.individual.IndividualResponseDetailDto;
import com.daangn.survey.domain.deprecated.aggregation.model.individual.SurveyResponsesBrief;
import com.daangn.survey.domain.deprecated.aggregation.service.AggregationService;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.deprecated.response.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "집계 엔드포인트")
@RequiredArgsConstructor
@RequestMapping("/api/v1/aggregation")
@RestController
public class AggregationController {

    private final ResponseService responseService;
    private final AggregationService aggregationService;

    // TODO: 권한 체크
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

    @Operation(summary = "답변 요약 정보 조회", description = "설문 답변 요약 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요약 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = SurveyResponsesBrief.class))),
            @ApiResponse(responseCode = "401", description = "요약 정보 조회 실패 (권한 에러)", content = @Content)
    })
    @GetMapping("/{surveyId}/responses/brief")
    public ResponseEntity<ResponseDto<SurveyResponsesBrief>> getResponsesBrief(@PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_RESPONSES_BRIEF, aggregationService.getSurveyResponsesBrief(surveyId)));
    }

    @Operation(summary = "개별 답변 정보 조회", description = "개별 답변 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "개별 답변 정보 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = IndividualResponseDetailDto.class)))),
            @ApiResponse(responseCode = "401", description = "개별 답변 정보 조회 실패 (권한 에러)", content = @Content)
    })
    @GetMapping("/individual/{responseId}")
    public ResponseEntity<ResponseDto<List<IndividualResponseDetailDto>>> getIndividualResponse(@PathVariable Long responseId){
       return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_INDIVIDUAL, aggregationService.getIndividualSurveyResponse(responseService.getSurveyResponse(responseId))));
    }
}
