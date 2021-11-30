package com.daangn.survey.mongo.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@Document(collection = "sequence")
public class Sequence {
    @Id
    private String id;
    private Long seq;
}
