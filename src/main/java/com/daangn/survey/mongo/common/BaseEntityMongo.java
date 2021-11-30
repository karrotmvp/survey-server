package com.daangn.survey.mongo.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@Document
public class BaseEntityMongo {
    @Id
    private Long id;
}
