package com.daangn.survey.domain.deprecated.response.model.entity;

import com.daangn.survey.common.model.BaseEntity;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.deprecated.survey.survey.model.entity.Survey;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "survey_response")
public class SurveyResponse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_response_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
