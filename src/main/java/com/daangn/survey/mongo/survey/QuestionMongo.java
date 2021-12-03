package com.daangn.survey.mongo.survey;

import com.daangn.survey.mongo.common.BaseEntityMongo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Document(collection = "question")
public class QuestionMongo extends BaseEntityMongo {

    @NotNull
    private int questionType;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String text;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String description;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ChoiceMongo> choices;
}

