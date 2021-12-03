package com.daangn.survey.domain.survey.question.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestion is a Querydsl query type for Question
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QQuestion extends EntityPathBase<Question> {

    private static final long serialVersionUID = -512828726L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestion question = new QQuestion("question");

    public final com.daangn.survey.common.model.QBaseEntity _super = new com.daangn.survey.common.model.QBaseEntity(this);

    public final ListPath<Choice, QChoice> choices = this.<Choice, QChoice>createList("choices", Choice.class, QChoice.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> number = createNumber("number", Integer.class);

    public final QQuestionType questionType;

    public final com.daangn.survey.domain.survey.survey.model.entity.QSurvey survey;

    public final StringPath text = createString("text");

    public QQuestion(String variable) {
        this(Question.class, forVariable(variable), INITS);
    }

    public QQuestion(Path<? extends Question> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestion(PathMetadata metadata, PathInits inits) {
        this(Question.class, metadata, inits);
    }

    public QQuestion(Class<? extends Question> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.questionType = inits.isInitialized("questionType") ? new QQuestionType(forProperty("questionType")) : null;
        this.survey = inits.isInitialized("survey") ? new com.daangn.survey.domain.survey.survey.model.entity.QSurvey(forProperty("survey"), inits.get("survey")) : null;
    }

}

