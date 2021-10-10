package com.daangn.survey.core.auth.userDetails;

import com.daangn.survey.domain.member.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
//    private final MemberService memberService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String daangnUserId) throws UsernameNotFoundException {
        Member member = new Member() {}; // memberService.findMemberByDaangnUserId(daangnUserId);

        return new UserDetailsImpl(member);
    }
}