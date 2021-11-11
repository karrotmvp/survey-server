package com.daangn.survey.admin.repository;

import com.daangn.survey.admin.dto.AdminMemberDto;
import com.daangn.survey.admin.dto.AdminResponseDto;
import com.daangn.survey.admin.dto.AdminSurveyDto;
import com.daangn.survey.domain.etc.notification.model.entity.QNotification;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.member.model.entity.QMember;
import com.daangn.survey.domain.response.model.entity.QSurveyResponse;
import com.daangn.survey.domain.survey.model.entity.QSurvey;
import com.daangn.survey.domain.survey.model.entity.Target;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class QueryRepository {
    private final JPAQueryFactory queryFactory;

    // Member
    public List<AdminMemberDto> getAllBizProfiles(){
        QMember member = QMember.member;
        QNotification notification = QNotification.notification;
        QSurvey survey = QSurvey.survey;

        return queryFactory
                .select(Projections.fields(AdminMemberDto.class,
                        member.id.as("memberId"),
                        member.daangnId.as("daangnId"),
                        member.name,
                        survey.member.id.count().as("surveyCount"),
                        member.region
                ))
                .from(member)
                .innerJoin(member.surveys, survey)
                .groupBy(survey.member.id)
                .where(member.role.eq("ROLE_BIZ"))
                .fetch();
    }

    public List<AdminMemberDto> getAllUsers(){
        QMember member = QMember.member;
        QSurvey survey = QSurvey.survey;

        return queryFactory
                .select(Projections.fields(AdminMemberDto.class,
                        member.id.as("memberId"),
                        member.daangnId.as("daangnId"),
                        member.name,
                        survey.member.id.count().as("surveyCount"),
                        member.region
                ))
                .from(member)
                .innerJoin(member.surveys, survey)
                .groupBy(survey.member.id)
                .where(member.role.eq("ROLE_USER"))
                .fetch();

    }

    public List<AdminMemberDto> getMembersByCondition(){
        QMember member = QMember.member;
        QSurvey survey = QSurvey.survey;

        return queryFactory
                .select(Projections.fields(AdminMemberDto.class,
                        member.id.as("memberId"),
                        member.daangnId.as("daangnId"),
                        member.name,
                        survey.member.id.count().as("surveyCount"),
                        member.region
                        ))
                .from(member)
                .innerJoin(member.surveys, survey)
                .groupBy(survey.member.id)
                .where(member.surveys.size().gt(0).and(member.role.eq("ROLE_BIZ")))
                .fetch();

    }

    // Survey
    public List<AdminSurveyDto> getAdminSurveys(){
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
                .innerJoin(survey.surveyResponses, surveyResponse)
                .groupBy(surveyResponse.survey.id)
                .fetch();
    }

    public List<AdminSurveyDto> getAdminSurveysByMemberId(Long memberId){
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
                .innerJoin(survey.surveyResponses, surveyResponse)
                .where(survey.member.id.eq(memberId))
                .groupBy(surveyResponse.survey.id)
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
                .innerJoin(survey.surveyResponses, surveyResponse)
                .where(survey.publishedAt.isNotNull())
                .groupBy(surveyResponse.survey.id)
                .fetch();
    }

    // Response
    public List<AdminResponseDto> getAdminResponses(Long surveyId){
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
                .where(surveyResponse.survey.id.eq(surveyId))
                .fetch();
    }

    public List<AdminResponseDto> getAllAdminResponses(){
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
                .fetch();
    }

}
