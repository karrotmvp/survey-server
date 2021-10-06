package com.daangn.survey.question.model.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "question_type")
public class QuestionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

}
