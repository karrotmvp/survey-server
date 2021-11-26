package com.daangn.survey.mongo;

import com.daangn.survey.common.message.ResponseMessage;
import com.daangn.survey.common.model.ResponseDto;
import com.daangn.survey.domain.survey.model.dto.SurveyRequestDto;
import com.daangn.survey.mongo.response.ResponseMongo;
import com.daangn.survey.mongo.survey.SurveyMongo;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/mongo")
@RestController
public class MongoController {
    private final MongoService mongoService;
    private final Gson gson;

    @PostMapping("/survey")
    public ResponseEntity<ResponseDto<?>> insertSurvey(@RequestBody Map<String, Object> requestBody){
        SurveyMongo surveyMongo = gson.fromJson(gson.toJson(requestBody), SurveyMongo.class);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE, mongoService.insertOne(surveyMongo)));
    }

    @PostMapping("/response")
    public ResponseEntity<ResponseDto<?>> insertResponse(@RequestBody Map<String, Object> requestBody){
        ResponseMongo responseMongo = gson.fromJson(gson.toJson(requestBody), ResponseMongo.class);


        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE, mongoService.insertOne(responseMongo)));
    }

    @GetMapping("/aggregate/{surveyId}")
    public ResponseEntity<ResponseDto<?>> getAggregation(@PathVariable Long surveyId){

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE, mongoService.getAggregation(surveyId)));
    }
}
