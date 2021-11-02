package com.daangn.survey.domain.member.service;

import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.EntityNotFoundException;
import com.daangn.survey.domain.etc.notification.model.entity.Notification;
import com.daangn.survey.domain.etc.notification.repository.NotificationRepository;
import com.daangn.survey.domain.member.model.entity.BizProfile;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.member.model.entity.QMember;
import com.daangn.survey.domain.member.model.mapper.MemberMapper;
import com.daangn.survey.domain.member.repository.BizProfileRepository;
import com.daangn.survey.domain.member.repository.MemberRepository;
import com.daangn.survey.domain.survey.model.entity.QSurvey;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BizProfileRepository bizProfileRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final MemberMapper memberMapper;
    private final NotificationRepository notificationRepository;

    @Transactional
    public Member updateMember(Member newMember){
        Optional<Member> oldMember = memberRepository.findMemberByDaangnId(newMember.getDaangnId());

        if(oldMember.isPresent()) {
            memberMapper.updateMember(oldMember.get(), newMember);
            return oldMember.get();
        } else
            return memberRepository.save(newMember);
    }


    @Transactional
    public BizProfile updateBizProfile(String businessId){

        Optional<BizProfile> bizProfile = bizProfileRepository.findBizProfileByBusinessId(businessId);

        if(bizProfile.isPresent())
            return null; // TODO: bizProfile.get().updateProfile();
        else
            return bizProfileRepository.save(BizProfile.builder().businessId(businessId).build());
    }

    @Transactional(readOnly = true)
    public Member findByDaangnId(String daangnId){
        return memberRepository.findMemberByDaangnId(daangnId)
                                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    // Todo: 캐시 기능 넣기
    @Transactional(readOnly = true)
    public List<Member> getAllMembers(){
        QMember member = QMember.member;
        QSurvey survey = QSurvey.survey;

        notificationRepository.findAll();

        return jpaQueryFactory.selectFrom(member)
                .leftJoin(member.surveys, survey).distinct()
                .fetchJoin()
                .fetch();
    }

    // Todo: 유연한 필터 만들기
    @Transactional(readOnly = true)
    public List<Member> getMembersByCondition(){
        QMember member = QMember.member;
        QSurvey survey = QSurvey.survey;

        notificationRepository.findAll(); // Todo: 이건 너무 비효율적인 거 아닐까?

        return jpaQueryFactory.selectFrom(member)
                .leftJoin(member.surveys, survey).distinct()
                .fetchJoin()
                .where(member.surveys.size().gt(0))
                .fetch();

    }
}
