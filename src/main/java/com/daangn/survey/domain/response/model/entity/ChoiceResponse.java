package com.daangn.survey.domain.response.model.entity;

import com.daangn.survey.common.entity.BaseEntity;
import com.daangn.survey.domain.question.model.entity.Choice;
import com.daangn.survey.domain.question.model.entity.Question;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "choice_response")
public class ChoiceResponse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "choice_id")
    private Choice choice;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "survey_response_id")
    private SurveyResponse surveyResponse;
    
//    @EmbeddedId
//    private ChoiceResponsePK choiceResponsePK;
//
//    @MapsId("surveyResponseId")
//    @ManyToOne
//    @JoinColumn(name = "survey_response_id")
//    private SurveyResponse surveyResponse;
//
//    @MapsId("questionId")
//    @ManyToOne
//    @JoinColumn(name = "question_id")
//    private Question question;
//
//    @MapsId("choiceId")
//    @ManyToOne
//    @JoinColumn(name = "choice_id")
//    private Choice choice;

}
