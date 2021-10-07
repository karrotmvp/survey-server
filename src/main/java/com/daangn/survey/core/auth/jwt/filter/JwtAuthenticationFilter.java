package com.daangn.survey.core.auth.jwt.filter;

import com.daangn.survey.core.auth.jwt.component.JwtResolver;
import com.daangn.survey.core.auth.jwt.component.JwtValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtResolver jwtResolver;
    private final JwtValidator jwtValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain)
                                                                                                                    throws IOException, ServletException {
        String token = jwtResolver.resolveToken(servletRequest);

        if (jwtValidator.validateToken(token)) {
            setAuthToSecurityContextHolder(token);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setAuthToSecurityContextHolder(String token) {
        Authentication auth = jwtResolver.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }



}
