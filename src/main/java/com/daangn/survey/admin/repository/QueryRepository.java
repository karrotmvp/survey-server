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

    public List<AdminMemberDto> getAllBizProfiles(){
        QMember member = QMember.member;
        QNotification notification = QNotification.notification;

        return queryFactory
                .select(Projections.fields(AdminMemberDto.class,
                        member.id.as("memberId"),
                        member.daangnId.as("daangnId"),
                        member.name,
                        member.surveys.size().as("surveyCount"),
                        member.region
                ))
                .from(member)
                .where(member.role.eq("ROLE_BIZ"))
                .fetch();
    }

    public List<AdminMemberDto> getAllUsers(){
        QMember member = QMember.member;

        return queryFactory
                .select(Projections.fields(AdminMemberDto.class,
                        member.id.as("memberId"),
                        member.daangnId.as("daangnId"),
                        member.name,
                        member.surveys.size().as("surveyCount"),
                        member.region
                ))
                .from(member)
                .where(member.role.eq("ROLE_USER"))
                .fetch();

    }

    public List<AdminMemberDto> getMembersByCondition(){
        QMember member = QMember.member;

        return queryFactory
                .select(Projections.fields(AdminMemberDto.class,
                        member.id.as("memberId"),
                        member.daangnId.as("daangnId"),
                        member.name,
                        member.surveys.size().as("surveyCount"),
                        member.region
                        ))
                .from(member)
                .where(member.surveys.size().gt(0).and(member.role.eq("ROLE_BIZ")))
                .fetch();

    }

    public List<AdminSurveyDto> getAdminSurveys(){
        QSurvey survey = QSurvey.survey;

        return queryFactory
                .select(Projections.fields(AdminSurveyDto.class,
                    survey.id.as("surveyId"),
                    survey.member.name.as("writer"),
                    survey.title,
                    survey.target,
                    survey.surveyResponses.size().as("responseCount"),
                    survey.publishedAt
                ))
                .from(survey)
                .fetch();
    }

    public List<AdminSurveyDto> getAdminSurveysByMemberId(Long memberId){
        QSurvey survey = QSurvey.survey;

        return queryFactory
                .select(Projections.fields(AdminSurveyDto.class,
                        survey.id.as("surveyId"),
                        survey.member.name.as("writer"),
                        survey.title,
                        survey.target,
                        survey.surveyResponses.size().as("responseCount"),
                        survey.publishedAt
                ))
                .from(survey)
                .where(survey.member.id.eq(memberId))
                .fetch();
    }

    public List<AdminSurveyDto> getAdminSurveysAboutPublished(){
        QSurvey survey = QSurvey.survey;

        return queryFactory
                .select(Projections.fields(AdminSurveyDto.class,
                        survey.id.as("surveyId"),
                        survey.member.name.as("writer"),
                        survey.title,
                        survey.target,
                        survey.surveyResponses.size().as("responseCount"),
                        survey.publishedAt
                ))
                .from(survey)
                .where(survey.publishedAt.isNotNull())
                .fetch();
    }

    public List<AdminResponseDto> getAdminResponses(Long surveyId){
        QSurveyResponse surveyResponse = QSurveyResponse.surveyResponse;

        return queryFactory
                .select(Projections.fields(AdminResponseDto.class,
                        surveyResponse.id.as("responseId"),
                        surveyResponse.member.name.as("member"),
                        surveyResponse.createdAt
                        ))
                .from(surveyResponse)
                .where(surveyResponse.survey.id.eq(surveyId))
                .fetch();
    }
}
