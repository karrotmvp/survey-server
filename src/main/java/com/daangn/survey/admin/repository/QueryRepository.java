package com.daangn.survey.admin.repository;

import com.daangn.survey.admin.dto.AdminMemberDto;
import com.daangn.survey.admin.dto.AdminResponseDto;
import com.daangn.survey.admin.dto.AdminSurveyDto;
import com.daangn.survey.domain.member.model.entity.QMember;
import com.daangn.survey.domain.response.model.entity.QSurveyResponse;
import com.daangn.survey.domain.survey.survey.model.entity.QSurvey;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class QueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * Members
     */
    public List<AdminMemberDto> getUsersWhere(Integer number, String role){
        QMember member = QMember.member;
        QSurvey survey = QSurvey.survey;

        return queryFactory
                .select(Projections.fields(AdminMemberDto.class,
                        member.id.as("memberId"),
                        member.daangnId.as("daangnId"),
                        member.name,
                        survey.member.id.count().as("surveyCount"),
                        member.region,
                        member.imageUrl
                ))
                .from(member)
                .leftJoin(survey).on(member.id.eq(survey.member.id))
                .groupBy(member.member.id)
                .orderBy(member.createdAt.asc())
                .where(QueryExpression.hasSurveys(number), QueryExpression.eqRole(role))
                .fetch();

    }

    /**
     * Surveys
     */
    public List<AdminSurveyDto> getAdminSurveysWhere(Long memberId){
        QSurvey survey = QSurvey.survey;
        QMember member = QMember.member;
        QSurveyResponse surveyResponse = QSurveyResponse.surveyResponse;

        return queryFactory
                .select(Projections.fields(AdminSurveyDto.class,
                        survey.id.as("surveyId"),
                        survey.member.name.as("writer"),
                        survey.title,
                        survey.target,
                        surveyResponse.id.count().as("responseCount"),
                        survey.publishedAt
                ))
                .from(survey)
                .innerJoin(survey.member, member)
                .leftJoin(survey.surveyResponses, surveyResponse)
                .where(QueryExpression.eqMemberIdAboutSurvey(memberId))
                .groupBy(survey.survey.id)
                .fetch();
    }

    public List<AdminSurveyDto> getAdminSurveysAboutPublished(){
        QSurvey survey = QSurvey.survey;
        QMember member = QMember.member;
        QSurveyResponse surveyResponse = QSurveyResponse.surveyResponse;

        return queryFactory
                .select(Projections.fields(AdminSurveyDto.class,
                        survey.id.as("surveyId"),
                        survey.member.name.as("writer"),
                        survey.title,
                        survey.target,
                        surveyResponse.id.count().as("responseCount"),
                        survey.publishedAt
                ))
                .from(survey)
                .innerJoin(survey.member, member)
                .leftJoin(survey.surveyResponses, surveyResponse)
                .where(survey.publishedAt.isNotNull())
                .groupBy(survey.survey.id)
                .orderBy(survey.publishedAt.desc())
                .fetch();
    }

    /**
     * Responses
     */
    public List<AdminResponseDto> getAdminResponsesWhere(Long surveyId){
        QSurveyResponse surveyResponse = QSurveyResponse.surveyResponse;
        QMember member = QMember.member;
        QSurvey survey = QSurvey.survey;

        return queryFactory
                .select(Projections.fields(AdminResponseDto.class,
                        surveyResponse.id.as("responseId"),
                        surveyResponse.member.name.as("member"),
                        survey.title.as("surveyTitle"),
                        surveyResponse.createdAt
                        ))
                .from(surveyResponse)
                .innerJoin(surveyResponse.member, member)
                .innerJoin(surveyResponse.survey, survey)
                .where(QueryExpression.eqSurveyIdAboutSurveyResponse(surveyId))
                .fetch();
    }
}
