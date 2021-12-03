package com.daangn.survey.mongo.survey;

import com.daangn.survey.domain.survey.survey.model.entity.Target;
import com.daangn.survey.mongo.common.BaseEntityMongo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.reducing;

@Getter
@Setter
@Builder
@Document(collection = "survey")
public class SurveyMongo extends BaseEntityMongo {

    private Long memberId;

    private String title;

    private int target;

    private List<QuestionMongo> questions;

    private LocalDateTime createdAt;

    public int getSurveyEstimatedTime(){
        return getQuestions().stream().map(el -> el.getQuestionEstimatedTime()).collect(reducing(Integer::sum)).get();
    }

    public String convertTarget(){
        return Target.findValue(target);
    }

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


