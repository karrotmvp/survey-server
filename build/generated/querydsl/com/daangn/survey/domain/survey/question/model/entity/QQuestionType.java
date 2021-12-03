package com.daangn.survey.domain.survey.question.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QQuestionType is a Querydsl query type for QuestionType
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QQuestionType extends EntityPathBase<QuestionType> {

    private static final long serialVersionUID = -2051512028L;

    public static final QQuestionType questionType = new QQuestionType("questionType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QQuestionType(String variable) {
        super(QuestionType.class, forVariable(variable));
    }

    public QQuestionType(Path<? extends QuestionType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuestionType(PathMetadata metadata) {
        super(QuestionType.class, metadata);
    }

}

