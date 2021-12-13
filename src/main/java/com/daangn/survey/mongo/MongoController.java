package com.daangn.survey.mongo;

import com.daangn.survey.common.message.ResponseMessage;
import com.daangn.survey.common.model.ResponseDto;
import com.daangn.survey.core.annotation.CurrentUser;
import com.daangn.survey.domain.deprecated.aggregation.model.individual.SurveyResponsesBrief;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.deprecated.survey.survey.model.dto.SurveyBriefDto;
import com.daangn.survey.mongo.aggregate.AggregationQuestionMongo;
import com.daangn.survey.mongo.aggregate.individual.IndividualQuestionMongo;
import com.daangn.survey.mongo.response.dto.ResponseMongoDto;
import com.daangn.survey.mongo.response.dto.ResponseMongoRequestDto;
import com.daangn.survey.mongo.survey.SurveyMongo;
import com.daangn.survey.mongo.survey.SurveySummaryMongoDto;
import com.daangn.survey.mongo.survey.dto.SurveyMongoDto;
import com.daangn.survey.mongo.survey.dto.SurveyMongoRequestDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.daangn.survey.common.message.ResponseMessage.*;

@Tag(name = "몽고 엔드포인트")
@RequiredArgsConstructor
@RequestMapping("/api/v1/mongo")
@RestController
public class MongoController {
    private final MongoService mongoService;
    private Gson gson;

    @PostConstruct
    public void initialize(){
        this.gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        ).create();
    }

    // Survey
    @Operation(summary = "설문 생성", description = "설문과 질문들을 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "설문 저장 성공", content = @Content)})
    @PostMapping("/surveys")
    public ResponseEntity<ResponseDto<?>> insertSurvey(
            @Parameter(description = "Member", hidden = true) @CurrentUser Member member,
            @Parameter(description = "requestBody", schema = @Schema(implementation = SurveyMongoRequestDto.class)) @RequestBody Map<String, Object> requestBody
    ){
        SurveyMongo surveyMongo = gson.fromJson(gson.toJson(requestBody), SurveyMongo.class);

        surveyMongo.setMemberId(member.getId());
        surveyMongo.setCreatedAt(LocalDateTime.now().plusHours(9L));
        surveyMongo.setDeleted(false);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.of(HttpStatus.CREATED, ResponseMessage.CREATE_SURVEY, mongoService.insertSurvey(surveyMongo)));
    }

    @Operation(summary = "설문 리스트 조회", description = "설문 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "설문 리스트 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SurveySummaryMongoDto.class)))),
            @ApiResponse(responseCode = "401", description = "설문 리스트 조회 실패 (권한 에러)", content = @Content)
    })
    @GetMapping("/surveys")
    public ResponseEntity<ResponseDto<List<?>>> getSurveys(
            @Parameter(description = "Member", hidden = true) @CurrentUser Member member
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, READ_SURVEY_LIST, mongoService.findSurveysByMemberId(member.getId())));
    }

    @Operation(summary = "설문 상세 조회", description = "설문 상세를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "설문 상세 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SurveyMongoDto.class)))),
            @ApiResponse(responseCode = "404", description = "설문 엔티티 조회 실패", content = @Content),
            @ApiResponse(responseCode = "401", description = "설문 리스트 조회 실패 (권한 에러)", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/surveys/{surveyId}")
    public ResponseEntity<ResponseDto<SurveyMongoDto>> getSurvey(@PathVariable Long surveyId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_SURVEY_DETAIL, mongoService.findSurvey(surveyId)));
    }

    @Operation(summary = "설문 요약 조회", description = "설문 요약 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "설문 요약 정보 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SurveyBriefDto.class)))),
            @ApiResponse(responseCode = "404", description = "설문 엔티티 조회 실패", content = @Content),
            @ApiResponse(responseCode = "401", description = "설문 리스트 조회 실패 (권한 에러)", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/surveys/brief/{surveyId}")
    public ResponseEntity<ResponseDto<SurveyBriefDto>> getSurveyBrief(@PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, READ_SURVEY_BRIEF, mongoService.findSurveyBriefBySurveyId(surveyId)));
    }

    @DeleteMapping("/surveys/{surveyId}")
    public ResponseEntity<ResponseDto<?>> deleteSurvey(@PathVariable Long surveyId){
        mongoService.deleteSurvey(surveyId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, DELETE_SURVEY));
    }

    // Response
    @Operation(summary = "답변 저장", description = "답변을 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "답변 저장 성공", content = @Content)})
    @PostMapping("/responses")
    public ResponseEntity<ResponseDto<?>> insertResponse(
            @Parameter(description = "Member", hidden = true) @CurrentUser Member member,
            @Parameter(description = "requestBody", schema = @Schema(implementation = ResponseMongoRequestDto.class)) @RequestBody Map<String, Object> requestBody
    ){
        ResponseMongoDto responseMongoDto = gson.fromJson(gson.toJson(requestBody), ResponseMongoDto.class);

        responseMongoDto.setMemberId(member.getId());
        mongoService.insertResponse(responseMongoDto);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.of(HttpStatus.CREATED, ResponseMessage.CREATE_RESPONSE));
    }

    // Aggregation
    @Operation(summary = "답변 통계 조회", description = "설문 결과를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "답변 결과 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AggregationQuestionMongo.class)))),
            @ApiResponse(responseCode = "401", description = "답변 결과 조회 실패 (권한 에러)", content = @Content)
    })
    @GetMapping("/aggregate/surveys/{surveyId}")
    public ResponseEntity<ResponseDto<List<AggregationQuestionMongo>>> getAggregation(@PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_AGGREGATION, mongoService.getAggregation(surveyId)));
    }

    @Operation(summary = "개별 답변 정보 조회", description = "개별 답변 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "개별 답변 정보 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = IndividualQuestionMongo.class)))),
            @ApiResponse(responseCode = "401", description = "개별 답변 정보 조회 실패 (권한 에러)", content = @Content)
    })
    @GetMapping("/surveys/{surveyId}/individual/{responseId}")
    public ResponseEntity<ResponseDto<List<IndividualQuestionMongo>>> getIndividualResponse(@PathVariable Long surveyId, @PathVariable Long responseId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_INDIVIDUAL, mongoService.getIndividualResponseMongo(surveyId, responseId)));
    }

    @Operation(summary = "답변 요약 정보 조회", description = "설문 답변 요약 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요약 정보 조회 성공",
                    content = @Content(schema = @Schema(implementation = SurveyResponsesBrief.class))),
            @ApiResponse(responseCode = "401", description = "요약 정보 조회 실패 (권한 에러)", content = @Content)
    })
    @GetMapping("/surveys/{surveyId}/responses/brief")
    public ResponseEntity<ResponseDto<SurveyResponsesBrief>> getResponsesBrief(@PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_RESPONSES_BRIEF, mongoService.getResponseBrief(surveyId)));
    }

}
