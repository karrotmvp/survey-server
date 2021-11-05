package com.daangn.survey.domain.member.util;

import com.daangn.survey.domain.member.model.entity.QMember;
import com.daangn.survey.domain.member.repository.BizProfileRepository;
import com.daangn.survey.domain.member.repository.MemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 현재의 멤버를 BizProfile 테이블로 옮겨주는 도구
 */
@Lazy
@Service
@RequiredArgsConstructor
public class BizProfileMigrator {

    private final MemberRepository memberRepository;
    private final BizProfileRepository bizProfileRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = true)
    public List<String> getMembersDaangnId(){
        QMember member = QMember.member;
        return jpaQueryFactory.select(member.daangnId).from(member).fetch();
    }


    // 유저가 로그인을 한다. -> 설문 작성하기를 누른다 -> 비즈프로필 프리셋을 통해 비즈프로필을 선택한다
    // -> 비즈프로필 정보를 가져온다(karrotApiUtil.resolveBusinessProfile()) -> 이미 있는 정보인지 본다.
    // -> 있으면 업데이트만 한다. -> 없으면 새로 만드는데, 이때 유저 정보를 넣으면 된다.
}
