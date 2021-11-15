package com.daangn.survey.common.util.shorturl.component;

import com.daangn.survey.common.util.shorturl.model.dto.ShortUrlResult;
import com.daangn.survey.common.util.shorturl.model.dto.ShortUrlType;
import com.daangn.survey.common.util.shorturl.model.entity.ShortUrl;
import com.daangn.survey.common.util.shorturl.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class UrlConvertService {

    private final UrlRepository urlRepository;
    private final UrlEncoder urlEncoder;

    // todo: 리팩토링하기
    @Transactional
    public ShortUrlResult getShortenUrl(String url, String schemeUrl){

        ShortUrlResult shortUrlResult = new ShortUrlResult();

        ShortUrl shortUrl = new ShortUrl();

        if(urlRepository.existsByShortUrlOrOriginUrl(url, url)){
            shortUrl = urlRepository.findFirstByShortUrlOrOriginUrlOrderByCreatedAt(url, url);
            shortUrlResult.setShortUrlType(shortUrl.getShortUrl().equals(url) ? ShortUrlType.SHORT : ShortUrlType.ORIGIN);
            shortUrl.addReqCount();
            shortUrlResult.setShortUrl(shortUrl);
        } else {

            ShortUrl curShortUrl = ShortUrl.builder().originUrl(url).schemeUrl(schemeUrl).build();
            shortUrl =  urlRepository.save(curShortUrl);

            String encodeUrl = "";

            encodeUrl = urlEncoder.urlEncoder(String.valueOf(shortUrl.getId()));

            while(existsUrl(encodeUrl)){
                encodeUrl = urlEncoder.urlEncoder(String.valueOf(shortUrl.getId()));
            }

            shortUrl.setShortUrl(encodeUrl);

            shortUrlResult.setShortUrl(shortUrl);
            shortUrlResult.setShortUrlType(ShortUrlType.SHORT);
        }

        shortUrlResult.setSuccessFlag(true);

        return shortUrlResult;
    }

    @Transactional(readOnly = true)
    public boolean existsUrl(String url){
        return urlRepository.existsByShortUrlOrOriginUrl(url, url);
    }

}
