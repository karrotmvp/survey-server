package com.daangn.survey.question.model.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "choice")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(nullable = false)
    private Integer order;

    @Column(nullable = false)
    private String value;

}
