package com.daangn.survey.admin.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import io.micrometer.core.instrument.util.StringUtils;

import static com.daangn.survey.domain.member.model.entity.QMember.member;
import static com.daangn.survey.domain.response.model.entity.QSurveyResponse.surveyResponse;
import static com.daangn.survey.domain.survey.model.entity.QSurvey.survey;

public class QueryExpression {
    public static BooleanExpression eqRole(String role) {
        if (StringUtils.isEmpty(role)) return null;

        return member.role.eq(role);
    }

    public static BooleanExpression hasSurveys(Integer number){
        if(number == null) return null;

        return member.surveys.size().gt(number);
    }

    public static BooleanExpression eqSurveyIdAboutSurveyResponse(Long surveyId){
        if(surveyId == null || surveyId <= 0) return null;

        return surveyResponse.survey.id.eq(surveyId);
    }

    public static BooleanExpression eqMemberIdAboutSurvey(Long memberId){
        if(memberId == null || memberId <= 0) return null;

        return survey.member.id.eq(memberId);
    }
}
