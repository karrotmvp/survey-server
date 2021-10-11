package com.daangn.survey.domain.response.model.entity;

import com.daangn.survey.common.entity.BaseEntity;
import com.daangn.survey.domain.question.model.entity.Question;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "text_response")
public class TextResponse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "text_response_id")
    private Long id;


    @Column(nullable = false, length = 100)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "survey_response_id")
    private SurveyResponse surveyResponse;

//    @EmbeddedId
//    private TextResponsePK textResponsePK;
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

}
