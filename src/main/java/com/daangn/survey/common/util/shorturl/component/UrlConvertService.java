package com.daangn.survey.common.util.shorturl.component;

import com.daangn.survey.common.util.shorturl.model.dto.ShortUrlResult;
import com.daangn.survey.common.util.shorturl.model.dto.ShortUrlType;
import com.daangn.survey.common.util.shorturl.model.entity.ShortUrl;
import com.daangn.survey.common.util.shorturl.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

            //저장된 URL 정보가 없으면 새로 생성후
            //persist (save)해서 sequence를 먼저 생성하고 sequence 정보를 인코딩해
            //데이터베이스에 저장후 반대의 URL을 리턴
            //save Object
            ShortUrl curShortUrl = ShortUrl.builder().originUrl(url).schemeUrl(schemeUrl).build();
            //persist - generate sequence
            shortUrl =  urlRepository.save(curShortUrl);
            //encoding seq
            String encodeUrl = "";
            try{
                //시퀀스를 Base62로 인코딩한다.
                encodeUrl = encodingUrl(String.valueOf(shortUrl.getId()));
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                shortUrl.setShortUrl(encodeUrl);
            }
            shortUrlResult.setShortUrl(shortUrl);
            shortUrlResult.setShortUrlType(ShortUrlType.SHORT);
        }

        shortUrlResult.setSuccessFlag(true);

        return shortUrlResult;
    }

    private String encodingUrl(String seqStr) throws Exception{
        return urlEncoder.urlEncoder(seqStr);
    }

    @Transactional(readOnly = true)
    public boolean existsUrl(String url){
        return urlRepository.existsByShortUrlOrOriginUrl(url, url);
    }
}
