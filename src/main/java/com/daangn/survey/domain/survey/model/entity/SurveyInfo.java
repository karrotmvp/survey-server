package com.daangn.survey.domain.survey.model.entity;

import com.daangn.survey.domain.survey.model.mapper.WriterTypeConverter;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "survey_info")
public class SurveyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_info_id")
    private Long id;

    @Column(name = "writer_daangn_id")
    private String writerDaangnId;

    @Column(name = "writer_type")
    @Convert(converter = WriterTypeConverter.class)
    private WriterType writerType;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "survey_id")
    private Survey survey;

}
