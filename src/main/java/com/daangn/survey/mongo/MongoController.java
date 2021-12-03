package com.daangn.survey.mongo;

import com.daangn.survey.common.message.ResponseMessage;
import com.daangn.survey.common.model.ResponseDto;
import com.daangn.survey.core.annotation.CurrentUser;
import com.daangn.survey.domain.aggregation.model.individual.SurveyResponsesBrief;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.mongo.response.dto.ResponseMongoDto;
import com.daangn.survey.mongo.survey.SurveyMongo;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/v1/mongo")
@RestController
public class MongoController {
    private final MongoService mongoService;
    private final Gson gson;

    @PostMapping("/survey")
    public ResponseEntity<ResponseDto<?>> insertSurvey(@CurrentUser Member member, @RequestBody Map<String, Object> requestBody){
        SurveyMongo surveyMongo = gson.fromJson(gson.toJson(requestBody), SurveyMongo.class);

        surveyMongo.setMemberId(member.getId());
        mongoService.insertSurvey(surveyMongo);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE));
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
