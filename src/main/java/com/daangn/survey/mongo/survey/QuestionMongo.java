package com.daangn.survey.mongo.survey;

import com.daangn.survey.mongo.common.BaseEntityMongo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Document(collection = "question")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QuestionMongo extends BaseEntityMongo {

    @NotNull
    private int questionType;

    private String text;

    private String description;

    private List<ChoiceMongo> choices;

    public int getQuestionEstimatedTime(){
        if(questionType == 2) return 20;
        else if(questionType == 3) return 10;
        return 0;
    }
}

