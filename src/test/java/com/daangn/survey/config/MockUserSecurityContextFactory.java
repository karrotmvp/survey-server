package com.daangn.survey.config;

import com.daangn.survey.common.annotation.MockUser;
import com.daangn.survey.core.auth.userDetails.UserDetailsImpl;
import com.daangn.survey.domain.member.model.entity.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class MockUserSecurityContextFactory implements WithSecurityContextFactory<MockUser> {

    @Override
    public SecurityContext createSecurityContext(MockUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserDetailsImpl principal =
                new UserDetailsImpl(Member.builder()
                        .id(1L)
                        .name("test")
                        .role("ROLE_USER")
                        .daangnId("test")
                        .build());
        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
