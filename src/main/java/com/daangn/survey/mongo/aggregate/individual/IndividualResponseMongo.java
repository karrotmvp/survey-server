package com.daangn.survey.mongo.aggregate.individual;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IndividualResponseMongo {
    private String text;

    private String choice;
}
