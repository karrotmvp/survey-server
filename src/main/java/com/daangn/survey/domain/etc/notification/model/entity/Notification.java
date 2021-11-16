package com.daangn.survey.domain.etc.notification.model.entity;

import com.daangn.survey.common.model.BaseEntity;
import com.daangn.survey.domain.member.model.entity.Member;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "notification")
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "subject", nullable = false, length = 1000)
    private String subject;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "is_nofitying")
    private boolean isNotifying;

}
