package com.daangn.survey.mongo.response.dto;

import com.daangn.survey.mongo.common.BaseEntityMongo;
import com.daangn.survey.mongo.response.dto.AnswerMongoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@Document(collection = "response")
public class ResponseMongoDto extends BaseEntityMongo {

    private Long surveyId;

    private Long memberId;

    private List<AnswerMongoDto> answers;
}

