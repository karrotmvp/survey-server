package com.daangn.survey.mongo.survey;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Builder;
import org.bson.types.ObjectId;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Builder
public class QuestionMongo {
    @NotNull
    private int questionType;

    private Map<String, Object> data;

    @JsonAnySetter
    public void add(String key, Object value) {
        if (null == data) {
            data = new HashMap<>();
        }
        data.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> get() {
        return data;
    }
}

