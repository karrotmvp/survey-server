package com.daangn.survey.domain.member.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    @Schema(description = "당근 ID")
    private String daangnId;

    @Schema(description = "당근 이름")
    private String name;

    @Schema(description = "프로필 URL")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String imageUrl;

    @Schema(description = "역할")
    private String role;
}
