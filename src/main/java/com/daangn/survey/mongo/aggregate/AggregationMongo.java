package com.daangn.survey.mongo.aggregate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AggregationMongo {
//    private int order;
    private int count;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TextAnswer> texts;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String answer;

    @Getter @Setter
    @NoArgsConstructor
    public class TextAnswer{
        private Long responseId;
        private String answer;
    }
}
