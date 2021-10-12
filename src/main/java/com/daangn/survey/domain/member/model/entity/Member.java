package com.daangn.survey.domain.member.model.entity;

import com.daangn.survey.common.entity.BaseEntity;
import com.daangn.survey.domain.survey.model.entity.Survey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "member")
@DiscriminatorColumn(name = "DTYPE")
public abstract class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "daangn_user_id", nullable = false, unique = true)
    private String daangnUserId;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "member")
    private List<Survey> surveys;

}
