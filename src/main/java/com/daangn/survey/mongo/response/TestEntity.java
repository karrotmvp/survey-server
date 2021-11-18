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
@Document(collection = "survey")
public class TestEntity {
    @Id
    private ObjectId id;

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

