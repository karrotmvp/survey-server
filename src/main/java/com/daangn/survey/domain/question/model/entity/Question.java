package com.daangn.survey.domain.question.model.entity;

import com.daangn.survey.common.entity.BaseEntity;
import com.daangn.survey.domain.survey.model.entity.Survey;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @Column(length = 200)
    private String description;

    @OneToMany(mappedBy = "question", orphanRemoval = true)
    private List<Choice> choices;

    /**
     * 질문 타입이 3(객관식)인 경우, 선택지가 있어야 한다.
     * 질문 타입이 3(객관식)가 아닌 경우, 선택지가 없어야 한다.
     */

    public boolean checkQuestionTypeCondition(){
        if(getQuestionType().getId() == QuestionTypeCode.CHOICE_QUESTION.getNumber())
            return !choices.isEmpty();
        else
            return choices.isEmpty();
    }

}
