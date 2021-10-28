package com.daangn.survey.common.util.shorturl.controller;

import com.daangn.survey.common.util.shorturl.component.UrlConvertService;
import com.daangn.survey.common.util.shorturl.model.dto.ShortUrlResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/daangn/short-url")
public class UrlController {

    private final UrlConvertService urlConverter;

    @GetMapping("/convert")
    public ShortUrlResult convertShortUrl(@RequestParam(defaultValue = "") String urlStr){
        return urlConverter.getShortenUrl(urlStr.trim());
    }
}
