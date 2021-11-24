package com.daangn.survey.mongo.common;

import com.daangn.survey.mongo.survey.SurveyMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MongoEntitySequenceListener extends AbstractMongoEventListener<BaseEntityMongo> {

    private final SequenceGeneratorService generatorService;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<BaseEntityMongo> event){
        event.getSource().setId(generatorService.generateSequence(event.getCollectionName() + "_sequence"));
    }
}
