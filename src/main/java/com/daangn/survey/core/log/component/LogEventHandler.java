package com.daangn.survey.core.log.component;

import com.daangn.survey.common.util.shorturl.model.entity.ShortUrl;
import com.daangn.survey.common.util.shorturl.repository.UrlRepository;
import com.daangn.survey.core.log.LogRepository;
import com.daangn.survey.core.log.model.LogEvent;
import com.daangn.survey.core.log.model.ShortUrlLog;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class LogEventHandler {

    private final LogRepository logRepository;
    private final UrlRepository urlRepository;

    @Async
    @EventListener
    public void log(LogEvent event) {
        ShortUrl shortUrl = urlRepository.findShortUrlByShortUrl(event.getUrl());

        logRepository.saveUserLog(new ShortUrlLog(
                shortUrl.resolveSurveyId(),
                event.getUrl(),
                event.getUserAgent(),
                event.getReferer()
        ));
    }
}
