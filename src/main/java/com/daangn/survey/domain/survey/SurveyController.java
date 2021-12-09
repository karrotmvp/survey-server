package com.daangn.survey.domain.survey;

import com.daangn.survey.common.model.ResponseDto;
import com.daangn.survey.core.annotation.CurrentUser;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.survey.survey.model.dto.SurveyBriefDto;
import com.daangn.survey.domain.survey.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.survey.model.dto.SurveyRequestDto;
import com.daangn.survey.domain.survey.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.domain.survey.survey.service.SurveyService;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "설문 저장 성공", content = @Content)})
    @PostMapping
    public ResponseEntity<ResponseDto<?>> saveSurvey(@Parameter(description = "Member", hidden = true) @CurrentUser Member member,
                                                     @Parameter(description = "requestBody", schema = @Schema(implementation = SurveyRequestDto.class)) @RequestBody Map<String, Object> requestBody){

        Gson gson = new Gson();

        SurveyRequestDto surveyRequestDto = gson.fromJson(gson.toJson(requestBody), SurveyRequestDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(HttpStatus.CREATED, CREATE_SURVEY, surveyService.saveSurvey(member, surveyRequestDto)));
    }

    @Operation(summary = "설문 리스트 조회", description = "설문 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "설문 리스트 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SurveySummaryDto.class)))),
            @ApiResponse(responseCode = "401", description = "설문 리스트 조회 실패 (권한 에러)", content = @Content)
    })
    @GetMapping
    public ResponseEntity<ResponseDto<List<SurveySummaryDto>>> getSurveys(@Parameter(description = "Member", hidden = true) @CurrentUser Member member){

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, READ_SURVEY_LIST, surveyService.findSurveysByMemberId(member.getId())));
    }

    @Operation(summary = "설문 상세 조회", description = "설문 상세를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "설문 상세 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SurveyRequestDto.class)))),
            @ApiResponse(responseCode = "404", description = "설문 엔티티 조회 실패", content = @Content),
            @ApiResponse(responseCode = "401", description = "설문 리스트 조회 실패 (권한 에러)", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("{surveyId}")
    public ResponseEntity<ResponseDto<SurveyDto>> getSurveyDetail(@PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, READ_SURVEY_DETAIL, surveyService.findBySurveyId(surveyId)));
    }

    @Operation(summary = "설문 요약 조회", description = "설문 요약 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "설문 요약 정보 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SurveyBriefDto.class)))),
            @ApiResponse(responseCode = "404", description = "설문 엔티티 조회 실패", content = @Content),
            @ApiResponse(responseCode = "401", description = "설문 리스트 조회 실패 (권한 에러)", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/brief/{surveyId}")
    public ResponseEntity<ResponseDto<SurveyBriefDto>> getSurveyBrief(@PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, READ_SURVEY_BRIEF, surveyService.findSurveyBriefBySurveyId(surveyId)));
    }

    @Operation(summary = "설문 삭제", description = "설문을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "설문 삭제 성공", content = @Content),
            @ApiResponse(responseCode = "404", description = "설문 엔티티 조회 실패", content = @Content),
            @ApiResponse(responseCode = "401", description = "설문 리스트 조회 실패 (권한 에러)", content = @Content(schema = @Schema(hidden = true)))
    })
    @DeleteMapping("{surveyId}")
    public ResponseEntity<ResponseDto<?>> deleteSurvey(@Parameter(description = "Member", hidden = true) @CurrentUser Member member, @PathVariable Long surveyId){

        surveyService.deleteSurvey(surveyId, member.getId());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, DELETE_SURVEY));
    }
}
