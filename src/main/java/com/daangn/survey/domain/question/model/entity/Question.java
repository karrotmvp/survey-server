package com.daangn.survey.domain.question.model.entity;

import com.daangn.survey.common.entity.BaseEntity;
import com.daangn.survey.domain.survey.model.entity.Survey;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "question")
// Todo: CascadeType 신경쓰기
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @OneToOne
    @JoinColumn(name = "question_type_id")
    private QuestionType questionType;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String text;

}
