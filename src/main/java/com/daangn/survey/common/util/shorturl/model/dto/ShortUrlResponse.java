package com.daangn.survey.common.util.shorturl.model.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlResponse {
    private String shortUrl;
    private String originUrl;
}
