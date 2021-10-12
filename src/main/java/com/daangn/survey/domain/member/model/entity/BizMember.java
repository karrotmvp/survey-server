package com.daangn.survey.domain.member.model.entity;

import com.daangn.survey.domain.survey.model.entity.Survey;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DiscriminatorValue("B")
public class BizMember extends Member {
    @Column(name = "biz_name", nullable = false)
    private String bizName;

    @Builder
    public BizMember(Long id, String daangnUserId, String nickname, String imageUrl, String phone, List<Survey> surveys, String bizName) {
        super(id, daangnUserId, nickname, imageUrl, phone, surveys);
        this.bizName = bizName;
    }
}
