package com.daangn.survey.domain.survey.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "survey")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Builder.Default
    @Column(name = "is_published")
    private boolean isPublished = false;

    @Builder.Default
    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "survey", orphanRemoval = true)
    private List<Question> questions;
    
}
