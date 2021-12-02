package com.daangn.survey.domain.member.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BizProfileDto {
    @Schema(description = "당근 이름")
    private String name;

    @Schema(description = "이미지 URL")
    private String imageUrl;

    @Schema(description = "지역")
    private String region;

    @Schema(description = "프로필 URL")
    private String profileUrl;

    @Schema(description = "업종")
    private String bizCategory;

    @Schema(description = "단골 수")
    private Integer followersCount;

    @Schema(description = "커버 이미지 URLS")
    private List<String> coverImageUrls;
}
