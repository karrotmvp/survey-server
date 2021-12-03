package com.daangn.survey.domain.survey.survey.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSurveyInfo is a Querydsl query type for SurveyInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSurveyInfo extends EntityPathBase<SurveyInfo> {

    private static final long serialVersionUID = 2097234056L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSurveyInfo surveyInfo = new QSurveyInfo("surveyInfo");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSurvey survey;

    public final StringPath writerDaangnId = createString("writerDaangnId");

    public final EnumPath<WriterType> writerType = createEnum("writerType", WriterType.class);

    public QSurveyInfo(String variable) {
        this(SurveyInfo.class, forVariable(variable), INITS);
    }

    public QSurveyInfo(Path<? extends SurveyInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSurveyInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSurveyInfo(PathMetadata metadata, PathInits inits) {
        this(SurveyInfo.class, metadata, inits);
    }

    public QSurveyInfo(Class<? extends SurveyInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.survey = inits.isInitialized("survey") ? new QSurvey(forProperty("survey"), inits.get("survey")) : null;
    }

}

