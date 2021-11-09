package com.daangn.survey.admin.repository;

import com.daangn.survey.admin.dto.AdminSurveyDto;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.member.model.entity.QMember;
import com.daangn.survey.domain.survey.model.entity.QSurvey;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class QueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Member> getAllBizProfiles(){
        QMember member = QMember.member;
        QSurvey survey = QSurvey.survey;

        return queryFactory.selectFrom(member)
                .leftJoin(member.surveys, survey).distinct()
                .fetchJoin()
                .where(member.role.eq("ROLE_BIZ"))
                .fetch();
    }

    public List<Member> getAllUsers(){
        QMember member = QMember.member;
        QSurvey survey = QSurvey.survey;

        return queryFactory.selectFrom(member)
                .leftJoin(member.surveys, survey).distinct()
                .fetchJoin()
                .where(member.role.eq("ROLE_USER"))
                .fetch();
    }

    public List<Member> getMembersByCondition(){
        QMember member = QMember.member;
        QSurvey survey = QSurvey.survey;

        return queryFactory.selectFrom(member)
                .leftJoin(member.surveys, survey).distinct()
                .fetchJoin()
                .where(member.surveys.size().gt(0))
                .fetch();

    }

//    public List<AdminSurveyDto> getSurveysAboutPublished(){
//        QSurvey survey = QSurvey.survey;
//
//        return queryFactory.select(Projections.fields(AdminSurveyDto.class,
//                survey.id.as("surveyId"),
//                survey.title,
//                survey.target,
//                survey.publishedAt
//                )).from(survey)
//                .groupBy()
//    }
}
