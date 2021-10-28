package com.daangn.survey.common.util.shorturl.model.dto;

import com.daangn.survey.common.util.shorturl.model.entity.ShortUrl;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlResult {

    //url Entity
    private ShortUrl shortUrl;
    //result Data
    private ShortUrlType shortUrlType;
    //flag
    private boolean successFlag;
}
