package com.daangn.survey.response.model.entity;

import com.daangn.survey.common.entity.BaseEntity;
import com.daangn.survey.question.model.entity.Choice;
import com.daangn.survey.question.model.entity.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "choice_response")
public class ChoiceResponse extends BaseEntity {
    @EmbeddedId
    private ChoiceResponsePK choiceResponsePK;

    @MapsId("surveyResponseId")
    @ManyToOne
    @JoinColumn(name = "survey_response_id")
    private SurveyResponse surveyResponse;

    @MapsId("questionId")
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @MapsId("choiceId")
    @ManyToOne
    @JoinColumn(name = "choice_id")
    private Choice choice;

}
