package com.daangn.survey.domain.response.controller;

import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.common.message.ResponseMessage;
import com.daangn.survey.core.annotation.CurrentUser;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.response.model.dto.SurveyResponseDto;
import com.daangn.survey.domain.response.service.ResponseService;
import com.daangn.survey.domain.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.domain.survey.service.SurveyService;
import com.daangn.survey.domain.survey.service.SurveyServiceImpl;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.daangn.survey.common.message.ResponseMessage.READ_SURVEY_LIST;
import static com.daangn.survey.common.message.ResponseMessage.READ_USER_SURVEY_RESPONDED;

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

        Gson gson = new Gson();

        SurveyResponseDto surveyResponseDto = gson.fromJson(gson.toJson(requestBody), SurveyResponseDto.class);

        responseService.saveResponse(member, surveyResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(HttpStatus.CREATED, ResponseMessage.CREATE_RESPONSE));
    }

    @Operation(summary = "답변 결과 조회", description = "설문 결과를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "답변 결과 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SurveySummaryDto.class)))),
            @ApiResponse(responseCode = "401", description = "답변 결과 조회 실패 (권한 에러)", content = @Content)
    })
    @GetMapping("/surveys/{surveyId}/aggregation")
    public ResponseEntity<ResponseDto<List<SurveySummaryDto>>> getSurveyResponseAggregation(@Parameter(description = "Member", hidden = true) @CurrentUser Member member,
                                                                          @PathVariable Long surveyId){

        responseService.getAggregation(surveyId);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, READ_SURVEY_LIST));
    }

    @Operation(summary = "유저 답변 이력 조회", description = "유저의 답변 이력을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 답변 이력 조회 성공",
                    content = @Content(schema = @Schema(implementation = UserRespondedDto.class))),
            @ApiResponse(responseCode = "401", description = "유저 답변 이력 조회 실패 (권한 에러)", content = @Content)
    })
    @GetMapping("/surveys/{surveyId}/responded")
    public ResponseEntity<ResponseDto<UserRespondedDto>> getUserSurveyResponded(@Parameter(description = "Member", hidden = true) @CurrentUser Member member,
                                                                                            @PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, READ_USER_SURVEY_RESPONDED,
                new UserRespondedDto(responseService.respondedPrevious(member, surveyId))));
    }

    @AllArgsConstructor
    private class UserRespondedDto{
        public boolean responded;
    }
}
