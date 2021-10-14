package com.daangn.survey.core.auth.controller;

import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.core.auth.jwt.model.AccessToken;
import com.daangn.survey.core.auth.oauth.SocialResolver;
import com.daangn.survey.third.KarrotAccessToken;
import com.daangn.survey.third.KarrotUserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "인증 엔드포인트")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SocialResolver socialResolver;

    @Operation(summary = "액세스 토큰 생성", description = "액세스 토큰을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "액세스 토큰 생성", content = @Content)})
    @GetMapping("/customer")
    public ResponseEntity<ResponseDto<?>> createAccessToken(@RequestParam String code){
        KarrotAccessToken karrotAccessToken = (KarrotAccessToken) socialResolver.resolveAccessToken(code);
        KarrotUserDetail karrotUserDetail = (KarrotUserDetail) socialResolver.resolveUserDetails(karrotAccessToken.getAccessToken());

        return null;
    }
}
