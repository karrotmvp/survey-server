package com.daangn.survey.response.model.entity;

import com.daangn.survey.common.entity.BaseEntity;
import com.daangn.survey.question.model.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@Entity
@Table(name = "text_response")
public class TextResponse extends BaseEntity {
    @EmbeddedId
    private TextResponsePK textResponsePK;

    @Column(nullable = false, length = 100)
    private String answer;

    @MapsId("surveyResponseId")
    @ManyToOne
    @JoinColumn(name = "survey_response_id")
    private SurveyResponse surveyResponse;

    @MapsId("questionId")
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

}
