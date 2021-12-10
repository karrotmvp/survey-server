package com.daangn.survey.domain.deprecated.survey.question.model.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "question_type")
public class QuestionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_type_id")
    private Long id;

    @Column(length = 30)
    private String name;

    public int getQuestionEstimatedTime(){
        if(id == 2L) return 20;
        else if(id == 3L) return 10;
        return 0;
    }
}
