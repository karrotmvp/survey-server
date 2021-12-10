package com.daangn.survey.core.log.aop;

import com.daangn.survey.core.log.annotation.UserLogging;
import com.daangn.survey.core.log.model.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@Aspect
public class LogEventAspect implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;

    @Pointcut("@annotation(userLogging)")
    public void pointcut(UserLogging userLogging) {
    }

    @AfterReturning(pointcut = "pointcut(userLogging)")
    public void afterReturning(JoinPoint joinPoint, UserLogging userLogging){

        switch (userLogging.type()){
            case SHORT_URL:
                String referrer = null;
                String userAgent = null;
                String url = null;

                for(Object arg : joinPoint.getArgs()){
                    if(arg instanceof HttpServletRequest) {
                        HttpServletRequest request = (HttpServletRequest) arg;
                        referrer = request.getHeader("referer");
                        userAgent = request.getHeader("user-agent");
                    }
                    if(arg instanceof String){
                        url = (String) arg;
                    }
                }

                eventPublisher.publishEvent(new LogEvent(url, userAgent, referrer));
                break;

        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}