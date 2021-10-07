package com.daangn.survey.question.model.entity;

import com.daangn.survey.common.entity.BaseEntity;
import com.daangn.survey.survey.model.entity.Survey;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "question")
// Todo: CascadeType 신경쓰기
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @OneToOne
    @JoinColumn(name = "question_type_id")
    private QuestionType questionType;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Integer number;

}
