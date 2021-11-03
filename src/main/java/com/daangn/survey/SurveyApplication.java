package com.daangn.survey;

import com.amazonaws.services.sqs.model.Message;
import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.common.message.ResponseMessage;
import com.daangn.survey.third.messaging.sqs.SqsSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableJpaAuditing
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SurveyApplication {

    private final SqsSender sqsSender;

    public static void main(String[] args) {
        SpringApplication.run(SurveyApplication.class, args);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDto<?>> healthCheckForGet(){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE));
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDto<?>> healthCheckForPost(){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE));
    }

    @PostMapping("/message")
    public String send(@RequestBody Message message) throws JsonProcessingException {
        sqsSender.sendMessage(message);
        return "OK";
    }

}
