package com.daangn.survey.mongo.aggregate;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AggregationListMongo {
    private List<AggregationMongo> data;
}
