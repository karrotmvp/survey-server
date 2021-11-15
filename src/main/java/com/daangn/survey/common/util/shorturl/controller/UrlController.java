package com.daangn.survey.common.util.shorturl.controller;

import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.common.message.ResponseMessage;
import com.daangn.survey.common.util.shorturl.component.UrlConvertService;
import com.daangn.survey.common.util.shorturl.model.dto.ShortUrlResponse;
import com.daangn.survey.common.util.shorturl.model.dto.ShortUrlResult;
import com.daangn.survey.third.karrot.KarrotApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "단축 URL")
@Controller
@RequiredArgsConstructor
public class UrlController {

    private final UrlConvertService urlConverter;
    private final KarrotApiUtil apiUtil;

    @Value("${mudda.front-url}")
    private String frontUrl;

    @Value("${mudda.short-url}")
    private String shortUrl;

    // 이거 안 사용할 거 같은데 아닌가?
    @GetMapping("/daangn/short-url/convert")
    @ResponseBody
    public ShortUrlResult convertShortUrl(@RequestParam(defaultValue = "") String urlStr) {
        return urlConverter.getShortenUrl(urlStr.trim(), null);
    }

    @GetMapping("/scheme/redirect")
    public String redirectToOriginUrl(@RequestParam String url) {

        return "redirect:" + urlConverter.getShortenUrl(url.trim(), null).getShortUrl().getSchemeUrl();
    }

    @Operation(summary = "단축 URL 생성", description = "단축 URL을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "단축 URL 저장 성공", content = @Content(schema = @Schema(implementation = ShortUrlResponse.class)))})
    @ResponseBody
    @GetMapping("/api/v1/url/surveys/{surveyId}")
    public ResponseEntity<ResponseDto<ShortUrlResponse>> getSchemeUrl(@PathVariable Long surveyId) {

        String originUrl = frontUrl + "/survey/" + surveyId;
        String schemeUrl = "";

        if (!urlConverter.existsUrl(originUrl))
            schemeUrl = apiUtil.resolveSchemeUrl(frontUrl + "/survey/" + surveyId).getData().getWidget().getEntryTargetUri();

        ShortUrlResult result = urlConverter.getShortenUrl(originUrl, schemeUrl);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_SHORT_URL,
                        new ShortUrlResponse(shortUrl + "/scheme/redirect?url=" + result.getShortUrl().getShortUrl(), result.getShortUrl().getSchemeUrl())));
    }
}

