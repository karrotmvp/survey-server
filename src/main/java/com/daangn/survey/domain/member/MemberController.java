package com.daangn.survey.domain.member;

import com.daangn.survey.common.model.ResponseDto;
import com.daangn.survey.common.message.ResponseMessage;
import com.daangn.survey.core.annotation.CurrentUser;
import com.daangn.survey.domain.member.model.dto.MemberDto;
import com.daangn.survey.domain.member.model.entity.Member;
import com.daangn.survey.domain.member.model.mapper.MemberMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "사용자 엔드포인트")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberMapper memberMapper;

    @Operation(summary = "사용자 프로필 조회", description = "사용자 프로필을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 프로필 조회", content = @Content(schema = @Schema(implementation = MemberDto.class)))})
    @GetMapping("/me")
    public ResponseEntity<ResponseDto<?>> createCustomerAccessToken(@Parameter(description = "Member", hidden = true) @CurrentUser Member member){

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(HttpStatus.OK, ResponseMessage.READ_PROFILE, memberMapper.toDto(member)));
    }
}
