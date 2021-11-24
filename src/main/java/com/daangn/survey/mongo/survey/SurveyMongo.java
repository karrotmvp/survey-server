package com.daangn.survey.mongo.survey;

import com.daangn.survey.domain.survey.model.entity.Target;
import com.daangn.survey.mongo.common.BaseEntityMongo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Getter
@Setter
@Builder
@Document(collection = "survey")
public class SurveyMongo extends BaseEntityMongo{

    @Transient
    public static final String SEQUENCE_NAME = "survey_sequence";

//    @Id
//    private Long id;

    private String title;

    private int target;

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


