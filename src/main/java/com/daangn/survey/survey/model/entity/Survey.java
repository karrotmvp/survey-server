package com.daangn.survey.survey.model.entity;

import com.daangn.survey.question.model.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@Entity
@Table(name = "survey")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Builder.Default
    @Column(name = "is_published")
    private boolean isPublished = false;

    @OneToMany(mappedBy = "survey", orphanRemoval = true)
    private List<Question> questions;
    
}
