package com.daangn.survey.domain.member.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "biz_profile")
public class BizProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "biz_profile_id")
    private Long id;

    @Column(name = "business_id", nullable = false, unique = true)
    private String businessId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column
    private String region;

    @Column(name = "profile_url", length = 1000)
    private String profileUrl;

    @Column(name = "biz_category")
    private String bizCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
