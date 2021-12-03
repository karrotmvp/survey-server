package com.daangn.survey.domain.survey.survey.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSurvey is a Querydsl query type for Survey
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSurvey extends EntityPathBase<Survey> {

    private static final long serialVersionUID = -958563790L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSurvey survey = new QSurvey("survey");

    public final com.daangn.survey.common.model.QBaseEntity _super = new com.daangn.survey.common.model.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final com.daangn.survey.domain.member.model.entity.QMember member;

    public final DateTimePath<java.time.LocalDateTime> publishedAt = createDateTime("publishedAt", java.time.LocalDateTime.class);

    public final ListPath<com.daangn.survey.domain.survey.question.model.entity.Question, com.daangn.survey.domain.survey.question.model.entity.QQuestion> questions = this.<com.daangn.survey.domain.survey.question.model.entity.Question, com.daangn.survey.domain.survey.question.model.entity.QQuestion>createList("questions", com.daangn.survey.domain.survey.question.model.entity.Question.class, com.daangn.survey.domain.survey.question.model.entity.QQuestion.class, PathInits.DIRECT2);

    public final ListPath<com.daangn.survey.domain.response.model.entity.SurveyResponse, com.daangn.survey.domain.response.model.entity.QSurveyResponse> surveyResponses = this.<com.daangn.survey.domain.response.model.entity.SurveyResponse, com.daangn.survey.domain.response.model.entity.QSurveyResponse>createList("surveyResponses", com.daangn.survey.domain.response.model.entity.SurveyResponse.class, com.daangn.survey.domain.response.model.entity.QSurveyResponse.class, PathInits.DIRECT2);

    public final NumberPath<Integer> target = createNumber("target", Integer.class);

    public final StringPath title = createString("title");

    public QSurvey(String variable) {
        this(Survey.class, forVariable(variable), INITS);
    }

    public QSurvey(Path<? extends Survey> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSurvey(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSurvey(PathMetadata metadata, PathInits inits) {
        this(Survey.class, metadata, inits);
    }

    public QSurvey(Class<? extends Survey> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.daangn.survey.domain.member.model.entity.QMember(forProperty("member")) : null;
    }

}

