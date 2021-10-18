package com.daangn.survey.common.annotation;

import com.daangn.survey.config.MockUserSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockUserSecurityContextFactory.class)
public @interface MockUser {
}
