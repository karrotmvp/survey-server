package com.daangn.survey.core.auth.controller;

import com.daangn.survey.common.dto.ResponseDto;
import com.daangn.survey.common.message.ResponseMessage;
import com.daangn.survey.core.auth.jwt.component.JwtCreator;
import com.daangn.survey.core.auth.oauth.SocialResolver;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.member.service.MemberService;
import com.daangn.survey.domain.survey.model.dto.SurveyDto;
import com.daangn.survey.third.KarrotAccessToken;
import com.daangn.survey.third.KarrotBizProfileDetail;
import com.daangn.survey.third.KarrotUserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final MemberService memberService;
    private final JwtCreator jwtCreator;

    @Operation(summary = "고객 액세스 토큰 생성", description = "고객 액세스 토큰을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "액세스 토큰 생성", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)})
    @GetMapping("/customer")
    public ResponseEntity<ResponseDto<?>> createCustomerAccessToken(@RequestParam String code){
        KarrotAccessToken karrotAccessToken = (KarrotAccessToken) socialResolver.resolveAccessToken(code);
        KarrotUserDetail karrotUserDetail = (KarrotUserDetail) socialResolver.resolveUserDetails(karrotAccessToken.getAccessToken());

        Member member = memberService.updateMember(karrotUserDetail.getData().getUserId(), karrotUserDetail.getData().getNickname(), "ROLE_USER", null);
        String jwt = jwtCreator.createAccessToken(member);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, ResponseMessage.CREATE_JWT_CUSTOMER, jwt));
    }

    // Todo: @Deprecated
    @Operation(summary = "비즈니스 액세스 토큰 생성", description = "비즈니스 액세스 토큰을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "액세스 토큰 생성", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)})
    @GetMapping("/business")
    public ResponseEntity<ResponseDto<?>> createBusinessAccessToken(@RequestParam String bizProfileId){

        log.info("business 액세스 토큰 생성 호출");
        KarrotBizProfileDetail karrotBizProfileDetail = socialResolver.resolveBizProfileDetails(bizProfileId);
        log.info("비즈니스 프로필 불러오기 성공");

        Member member = memberService.updateMember(karrotBizProfileDetail.getData().getBizProfile().getId(), karrotBizProfileDetail.getData().getBizProfile().getName(), "ROLE_BIZ", karrotBizProfileDetail.getData().getBizProfile().getImageUrl());
        log.info("비즈니스 멤버 저장");

        String jwt = jwtCreator.createAccessToken(member);
        log.info("JWT: " + jwt);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, ResponseMessage.CREATE_JWT_BUSINESS, jwt));
    }
}
