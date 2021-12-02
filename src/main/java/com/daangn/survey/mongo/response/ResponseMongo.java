package com.daangn.survey.mongo.response;

import com.daangn.survey.mongo.common.BaseEntityMongo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Builder
@Document(collection = "response")
public class ResponseMongo extends BaseEntityMongo {

    private Long surveyId;

    private Long memberId;

    private List<AnswerMongo> answers;

}

