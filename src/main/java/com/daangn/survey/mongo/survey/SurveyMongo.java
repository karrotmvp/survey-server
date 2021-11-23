package com.daangn.survey.mongo.survey;

import com.daangn.survey.domain.survey.model.entity.Target;
import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.List;

@Builder
@Document(collection = "survey")
public class SurveyMongo {
    @Id
    private ObjectId id;

    private Long surveyId;

    private String title;

    @Enumerated(EnumType.ORDINAL)
    private Target target;

    private List<QuestionMongo> questions;

//    private Map<String, Object> data;
//
//    @JsonAnySetter
//    public void add(String key, Object value) {
//        if (null == data) {
//            data = new HashMap<>();
//        }
//        data.put(key, value);
//    }
//
//    @JsonAnyGetter
//    public Map<String, Object> get() {
//        return data;
//    }
}


