package com.daangn.survey.mongo.response;

import com.daangn.survey.mongo.common.BaseEntityMongo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "response")
public class ResponseMongo extends BaseEntityMongo {
    public static String sequenceName = "response_id";

    private Long responseId;

    private Long surveyId;

    private Long memberId;

    private Long questionId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String text;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String choice;

    private LocalDateTime createdAt;
}
