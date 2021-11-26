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
    public ResponseEntity<ResponseDto<?>> insert(@RequestBody Map<String, Object> requestBody){
        SurveyMongo surveyMongo = gson.fromJson(gson.toJson(requestBody), SurveyMongo.class);

        mongoService.insertOne(surveyMongo);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE));
    }

    @PostMapping("/response")
    public ResponseEntity<ResponseDto<?>> aggregateSurvey(@RequestBody Map<String, Object> requestBody){
        ResponseMongo responseMongo = gson.fromJson(gson.toJson(requestBody), ResponseMongo.class);

        mongoService.insertOne(responseMongo);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE));
    }

    @PostMapping("/aggregate")
    public ResponseEntity<ResponseDto<?>> getSurvey(@RequestBody Map<String, Object> requestBody){
        mongoService.saveSurveyResponseDto();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE, mongoService.getOne((Integer) requestBody.get("surveyId"))));
    }
}
