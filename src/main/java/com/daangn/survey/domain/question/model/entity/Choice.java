package com.daangn.survey.domain.question.model.entity;

import com.daangn.survey.common.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "choice")
public class Choice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private String value;

    public void setOrder(int order){
        this.number = order;
    }

    public Choice setQuestion(Question question){
        this.question = question;
        return this;
    }

}
