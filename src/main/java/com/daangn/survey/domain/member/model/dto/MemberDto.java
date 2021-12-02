package com.daangn.survey.domain.member.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MemberDto {
    @Schema(description = "당근 ID")
    private String daangnId;

    @Schema(description = "당근 이름")
    private String name;

    @Schema(description = "이미지 URL")
    private String imageUrl;

    @Schema(description = "역할")
    private String role;

    @Schema(description = "지역")
    private String region;

    @Schema(description = "프로필 URL")
    private String profileUrl;

    @Schema(description = "단골 수")
    private Integer followersCount;
}
