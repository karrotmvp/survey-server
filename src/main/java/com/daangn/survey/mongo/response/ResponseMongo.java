package com.daangn.survey.mongo.response;

import com.daangn.survey.mongo.common.BaseEntityMongo;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Document(collection = "response")
public class ResponseMongo extends BaseEntityMongo {

    private Long surveyId;

    private List<AnswerMongo> answers;

}

