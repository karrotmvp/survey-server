package com.daangn.survey.domain.survey.model.entity;

import com.daangn.survey.common.entity.BaseEntity;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.question.model.entity.Question;
import com.daangn.survey.domain.response.model.entity.SurveyResponse;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

import static java.util.stream.Collectors.reducing;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE survey SET is_deleted = true WHERE survey_id=?")
@Where(clause = "is_deleted = false")
@Table(name = "survey")
public class Survey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "target", nullable = false)
    private int target;

    @Builder.Default
    @Column(name = "is_published")
    private boolean isPublished = false;

    @Builder.Default
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "survey", orphanRemoval = true)
    private List<Question> questions;

    @OneToMany(mappedBy = "survey", orphanRemoval = true)
    private List<SurveyResponse> surveyResponses;

//    todo: 나중에 업데이트
//    @OneToOne(mappedBy = "survey", orphanRemoval = true)
//    private SurveyInfo surveyInfo;

    public boolean isWriter(Long memberId){
        return getMember().getId().equals(memberId);
    }

    public String convertTarget(){
        return Target.findValue(target);
    }

    public int getSurveyEstimatedTime(){
        return getQuestions().stream().map(el -> el.getQuestionType().getQuestionEstimatedTime()).collect(reducing(Integer::sum)).get();
    }
}
