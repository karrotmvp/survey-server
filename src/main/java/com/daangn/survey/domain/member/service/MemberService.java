package com.daangn.survey.domain.member.service;

import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.EntityNotFoundException;
import com.daangn.survey.domain.member.model.entity.BizProfile;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.member.repository.BizProfileRepository;
import com.daangn.survey.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BizProfileRepository bizProfileRepository;

    @Transactional
    public Member updateMember(String daangnId, String name, String role, String imageUrl){
        Optional<Member> member = memberRepository.findMemberByDaangnId(daangnId);

        if(member.isPresent())
            return member.get().updateProfile(name);
        else
            return memberRepository.save(Member.builder().daangnId(daangnId).role(role).name(name).imageUrl(imageUrl).build());
    }


    @Transactional
    public BizProfile updateBizProfile(String businessId){

        Optional<BizProfile> bizProfile = bizProfileRepository.findBizProfileByBusinessId(businessId);

        if(bizProfile.isPresent())
            return null; // TODO: bizProfile.get().updateProfile();
        else // TODO:
            return bizProfileRepository.save(BizProfile.builder().businessId(businessId).build());
    }

    @Transactional(readOnly = true)
    public Member findByDaangnId(String daangnId){
        return memberRepository.findMemberByDaangnId(daangnId)
                                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
