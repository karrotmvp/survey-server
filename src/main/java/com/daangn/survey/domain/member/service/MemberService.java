package com.daangn.survey.domain.member.service;

import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.EntityNotFoundException;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.member.model.mapper.MemberMapper;
import com.daangn.survey.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public Member updateMember(Member newMember){
        Optional<Member> oldMember = memberRepository.findMemberByDaangnId(newMember.getDaangnId());

        if(oldMember.isPresent()) {
            memberMapper.updateMember(oldMember.get(), newMember);
            return oldMember.get();
        } else
            return memberRepository.save(newMember);
    }

    @Transactional(readOnly = true)
    public Member findByDaangnId(String daangnId){

        return memberRepository.findMemberByDaangnId(daangnId)
                                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

}
