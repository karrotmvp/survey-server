package com.daangn.survey.admin.repository;

import com.daangn.survey.admin.dto.AdminSurveyDto;
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

    public List<AdminSurveyDto> getSurveysAboutPublished(){
        QMember member = QMember.member;
        QSurvey survey = QSurvey.survey;
        QSurveyResponse surveyResponse = QSurveyResponse.surveyResponse;

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

    /**
     * select  s.survey_id, m.name, s.title, s.target, s.published_at, COUNT(sr.survey_id) from survey as s
     *     left join member m on s.member_id = m.member_id
     *     inner join survey_response sr on s.survey_id = sr.survey_id
     * group by sr.survey_id
     */
}
