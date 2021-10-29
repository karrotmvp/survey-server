package com.daangn.survey.common.util.shorturl.controller;

import com.daangn.survey.common.util.shorturl.component.UrlConvertService;
import com.daangn.survey.common.util.shorturl.model.dto.ShortUrlResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/daangn/short-url")
public class UrlController {

    private final UrlConvertService urlConverter;

    // 이거 안 사용할 거 같은데 아닌가?
    @GetMapping("/convert")
    @ResponseBody
    public ShortUrlResult convertShortUrl(@RequestParam(defaultValue = "") String urlStr){
        return urlConverter.getShortenUrl(urlStr.trim());
    }

    @GetMapping("/path/{shortUrl}")
    public String redirectToOriginUrl(@PathVariable String shortUrl){

        return "redirect:" + urlConverter.getShortenUrl(shortUrl.trim()).getShortUrl().getOriginUrl();
    }
}
