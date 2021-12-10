package com.daangn.survey.domain.deprecated.survey.survey.model.entity;

import com.daangn.survey.common.model.BaseEntity;
import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.BusinessException;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.deprecated.survey.question.model.entity.Question;
import com.daangn.survey.domain.deprecated.response.model.entity.SurveyResponse;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Builder.Default
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "survey", orphanRemoval = true)
    private List<Question> questions;

    @OneToMany(mappedBy = "survey", orphanRemoval = true)
    private List<SurveyResponse> surveyResponses;

    public boolean isWriter(Long memberId){
        return getMember().getId().equals(memberId);
    }

    public String convertTarget(){
        return Target.findValue(target);
    }

    public int getSurveyEstimatedTime(){
        return getQuestions().stream().map(el -> el.getQuestionType().getQuestionEstimatedTime()).collect(reducing(Integer::sum)).get();
    }

    public void patchAboutPublishing(){
        if(this.publishedAt == null)
            this.publishedAt = LocalDateTime.now();
        else if(ChronoUnit.HOURS.between(this.publishedAt, LocalDateTime.now()) <= 24)
            this.publishedAt = null;
        else
            throw new BusinessException(ErrorCode.PATCH_ABOUT_PUBLISHING_NOT_ALLOWED);

    }
}
