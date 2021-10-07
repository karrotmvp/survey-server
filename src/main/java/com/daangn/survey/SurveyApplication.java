package com.daangn.survey;

import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.common.message.ResponseMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableJpaAuditing
public class SurveyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveyApplication.class, args);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDto<?>> healthCheck(){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, ResponseMessage.EXAMPLE));
    }

}
