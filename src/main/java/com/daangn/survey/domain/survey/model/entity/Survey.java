package com.daangn.survey.domain.survey.model.entity;

import com.daangn.survey.common.entity.BaseEntity;
import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.BusinessException;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.question.model.entity.Question;
import com.daangn.survey.domain.response.model.entity.SurveyResponse;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "survey")
public class Survey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;

    @ManyToOne
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

    public boolean isWriter(Long memberId){
        return getMember().getId().equals(memberId);
    }

    public void delete(){
        if(this.isDeleted == true) throw new BusinessException(ErrorCode.SURVEY_ALREADY_DELETED);

        this.isDeleted = true;
    }
}
