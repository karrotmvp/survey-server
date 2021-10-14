package com.daangn.survey.domain.member.service;

import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member updateMember(String daangnId, String name, String role, String imageUrl){
        Optional<Member> member = memberRepository.findByDaangnId(daangnId);

        if(member.isPresent())
            return member.get().updateProfile(name);
        else
            return memberRepository.save(Member.builder().daangnId(daangnId).role(role).name(name).imageUrl(imageUrl).build());
    }

    @Transactional(readOnly = true)
    public Member findByDaangnId(String daangnId){
        return memberRepository.findByDaangnId(daangnId)
                                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND.getMessage()));
    }
}
