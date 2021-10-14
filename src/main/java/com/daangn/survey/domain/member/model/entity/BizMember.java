//package com.daangn.survey.domain.member.model.entity;
//
//import com.daangn.survey.common.entity.BaseEntity;
//import com.daangn.survey.domain.survey.model.entity.Survey;
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.List;
//
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//@Entity
//@Table(name = "biz_member")
//public class BizMember extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "biz_member_id")
//    private Long id;
//
//    @Column(name = "daangn_id", nullable = false, unique = true)
//    private String daangnId;
//
//    @Column(name = "name", nullable = false)
//    private String name;
//
//    @Column(name = "image_url", nullable = false)
//    private String imageUrl;
//
//    @OneToMany(mappedBy = "member")
//    private List<Survey> surveys;
//}
