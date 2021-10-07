package com.daangn.survey.member.model.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@DiscriminatorValue("B")
public class BizMember extends Member {
    @Column(name = "biz_name", nullable = false)
    private String bizName;

}
