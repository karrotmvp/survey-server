package com.daangn.survey.mongo.response;

import com.daangn.survey.mongo.common.BaseEntityMongo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "response")
public class ResponseMongo extends BaseEntityMongo {
    private Long surveyId;
    private Long memberId;
    private Long questionId;
    private int questionType;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String text;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String choice;
}
