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


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    // Todo: biz 계정인 경우, businessId로 변경하기
    @Column(name = "daangn_id", nullable = false, unique = true)
    private String daangnId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false, length = 1000)
    private String profileUrl;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Survey> surveys;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<BizProfile> bizProfiles;

    public Member updateProfile(String name){
        this.name = name;

        return this;
    }

}
