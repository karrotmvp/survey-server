package com.daangn.survey.common.util.shorturl.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class UrlEncoder {

    private final int BASE62 = 62;
    private final String BASE62_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String encodeUrl(long param){
        StringBuffer sb = new StringBuffer();
        while(param > 0) {
            sb.append(BASE62_CHAR.charAt((int) (param % BASE62)));
            param /= BASE62;
        }

        return sb.toString();
    }

    public long decodeUrl(String param){
        long sum = 0;
        long power = 1;
        for (int i = 0; i < param.length(); i++) {
            sum += BASE62_CHAR.indexOf(param.charAt(i)) * power;
            power *= BASE62;
        }
        return sum;
    }

    public String urlEncoder(String seqStr) throws NoSuchAlgorithmException {
        String encodeStr = encodeUrl(Integer.valueOf(seqStr));
        log.info("base62 encode result:" + encodeStr);
        return encodeStr;
    }

    public long urlDecoder(String encodeStr) throws NoSuchAlgorithmException {
        long decodeVal = decodeUrl(encodeStr);
        log.info("base62 decode result:" + decodeVal);
        return decodeVal;
    }
}
