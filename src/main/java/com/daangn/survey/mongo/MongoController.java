package com.daangn.survey.mongo;

import com.daangn.survey.common.message.ResponseMessage;
import com.daangn.survey.common.model.ResponseDto;
import com.daangn.survey.core.annotation.CurrentUser;
import com.daangn.survey.domain.aggregation.model.individual.SurveyResponsesBrief;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.survey.survey.model.dto.SurveySummaryDto;
import com.daangn.survey.mongo.response.dto.ResponseMongoDto;
import com.daangn.survey.mongo.survey.SurveyMongo;
import com.daangn.survey.mongo.survey.SurveySummaryMongoDto;
import com.google.gson.*;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.daangn.survey.common.message.ResponseMessage.READ_SURVEY_LIST;

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
    @PostMapping("/survey")
    public ResponseEntity<ResponseDto<?>> insertSurvey(@CurrentUser Member member, @RequestBody Map<String, Object> requestBody){
        SurveyMongo surveyMongo = gson.fromJson(gson.toJson(requestBody), SurveyMongo.class);

        surveyMongo.setMemberId(member.getId());
        surveyMongo.setCreatedAt(LocalDateTime.now());
        mongoService.insertSurvey(surveyMongo);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE));
    }

    @GetMapping("/survey")
    public ResponseEntity<ResponseDto<List<SurveySummaryMongoDto>>> getSurveys(@Parameter(description = "Member", hidden = true) @CurrentUser Member member){

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, READ_SURVEY_LIST, mongoService.findSurveysByMemberId(member.getId())));
    }

    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<ResponseDto<?>> getSurvey(@PathVariable Long surveyId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE, mongoService.findSurvey(surveyId)));
    }

    @PostMapping("/response")
    public ResponseEntity<ResponseDto<?>> insertResponse(@CurrentUser Member member, @RequestBody Map<String, Object> requestBody){
        ResponseMongoDto responseMongoDto = gson.fromJson(gson.toJson(requestBody), ResponseMongoDto.class);

        responseMongoDto.setMemberId(member.getId());
        mongoService.insertResponse(responseMongoDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE));
    }

    @GetMapping("/aggregate/{surveyId}")
    public ResponseEntity<ResponseDto<?>> getAggregation(@PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE, mongoService.getAggregation(surveyId)));
    }

    @GetMapping("/surveys/{surveyId}/individual/{responseId}")
    public ResponseEntity<ResponseDto<?>> getIndividualResponse(@PathVariable Long surveyId, @PathVariable Long responseId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE, mongoService.getIndividualResponseMongo(surveyId, responseId)));
    }

    @GetMapping("/{surveyId}/responses/brief")
    public ResponseEntity<ResponseDto<SurveyResponsesBrief>> getResponsesBrief(@PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_RESPONSES_BRIEF, mongoService.getResponseBrief(surveyId)));
    }

}
