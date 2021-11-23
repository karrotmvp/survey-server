package com.daangn.survey.mongo.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Builder
@Document(collection = "response")
public class ResponseMongo {
    @Id
    private ObjectId id;

    private ObjectId surveyId;

    private Map<String, Object> answers;

    @JsonAnySetter
    public void add(String key, Object value) {
        if (null == answers) {
            answers = new HashMap<>();
        }
        answers.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> get() {
        return answers;
    }
}

