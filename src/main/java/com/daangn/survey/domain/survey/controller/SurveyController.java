package com.daangn.survey.domain.survey.controller;

import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.core.annotation.CurrentUser;
import com.daangn.survey.domain.member.model.entity.BizMember;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.domain.survey.model.dto.SurveySummaryDto;
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
    private final SurveyServiceImpl surveyService;

    @Operation(summary = "설문 생성", description = "설문과 질문들을 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "설문 저장 성공", content = @Content)})
    @PostMapping
    public ResponseEntity<ResponseDto<?>> saveSurvey(@Parameter(description = "Member", hidden = true) @CurrentUser Member member,
                                                     @Parameter(description = "responseBody", schema = @Schema(implementation = SurveyDto.class)) @RequestBody Map<String, Object> responseBody){

        if(member == null) member = BizMember.builder().id(1L).daangnUserId("test").bizName("testBiz").imageUrl("test").nickname("test").phone("tse").build();

        Gson gson = new Gson();

        SurveyDto surveyDto = gson.fromJson(gson.toJson(responseBody), SurveyDto.class);

        surveyService.saveSurvey(member, surveyDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(HttpStatus.OK, CREATE_SURVEY));
    }

    @Operation(summary = "설문 리스트 조회", description = "설문 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "설문 리스트 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SurveySummaryDto.class)))),
            @ApiResponse(responseCode = "401", description = "설문 리스트 조회 실패 (권한 에러)", content = @Content)
    })
    @GetMapping
    public ResponseEntity<ResponseDto<List<SurveySummaryDto>>> getSurveys(@Parameter(description = "Member", hidden = true) @CurrentUser Member member){

        if(member == null) member = BizMember.builder().id(1L).daangnUserId("test").bizName("testBiz").imageUrl("test").nickname("test").phone("tse").build();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, READ_SURVEY_LIST, surveyService.findAll(member.getId())));
    }

    @Operation(summary = "설문 상세 조회", description = "설문 상세를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "설문 상세 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SurveyDto.class)))),
            @ApiResponse(responseCode = "404", description = "설문 엔티티 조회 실패", content = @Content),
            @ApiResponse(responseCode = "401", description = "설문 리스트 조회 실패 (권한 에러)", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("{surveyId}")
    public ResponseEntity<ResponseDto<?>> getSurveyDetail(@PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, READ_SURVEY_DETAIL, surveyService.findBySurveyId(surveyId)));
    }

    @Operation(summary = "설문 삭제", description = "설문을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "설문 삭제 성공", content = @Content),
            @ApiResponse(responseCode = "404", description = "설문 엔티티 조회 실패", content = @Content),
            @ApiResponse(responseCode = "401", description = "설문 리스트 조회 실패 (권한 에러)", content = @Content(schema = @Schema(hidden = true)))
    })
    @DeleteMapping("{surveyId}")
    public ResponseEntity<ResponseDto<?>> deleteSurvey(@Parameter(description = "Member", hidden = true) @CurrentUser Member member, @PathVariable Long surveyId){

        if(member == null) member = BizMember.builder().id(1L).daangnUserId("test").bizName("testBiz").imageUrl("test").nickname("test").phone("tse").build();

        surveyService.deleteSurvey(surveyId, member.getId());
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, DELETE_SURVEY));
    }
}
