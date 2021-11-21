package com.daangn.survey.mongo;

import com.daangn.survey.common.message.ResponseMessage;
import com.daangn.survey.common.model.ResponseDto;
import com.daangn.survey.mongo.survey.SurveyMongo;
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

    @PostMapping("/survey")
    public ResponseEntity<ResponseDto<?>> insert(@RequestBody Map<String, Object> requestBody){

        mongoService.insertOne(SurveyMongo.builder().data(requestBody).build());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE, mongoService.getOne((Integer) requestBody.get("userId"))));
    }

    @PostMapping("/aggregate-survey")
    public ResponseEntity<ResponseDto<?>> aggregateSurvey(@RequestBody Map<String, Object> requestBody){

        mongoService.insertOne(SurveyMongo.builder().data(requestBody).build());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE, mongoService.getOne((Integer) requestBody.get("userId"))));
    }

    @PostMapping("/aggregate")
    public ResponseEntity<ResponseDto<?>> getSurvey(@RequestBody Map<String, Object> requestBody){
        mongoService.saveSurveyResponseDto();

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE, mongoService.getOne((Integer) requestBody.get("surveyId"))));
    }
}
