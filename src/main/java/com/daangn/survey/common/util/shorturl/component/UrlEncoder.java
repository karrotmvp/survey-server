package com.daangn.survey.common.util.shorturl.component;

import com.daangn.survey.core.error.ErrorCode;
import com.daangn.survey.core.error.exception.BusinessException;
import com.daangn.survey.core.error.exception.UrlEncodeError;
import com.google.common.hash.Hashing;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
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

    public String urlEncoder(String seqStr) {
        String encodeStr = "";
        try {
            encodeStr = encodeUrl(Long.parseLong(urlHashing(seqStr), 16));
        } catch (Exception e){
            Sentry.captureException(e);
            throw new UrlEncodeError(ErrorCode.SHORT_URL_ENCODE_FAILURE);
        }
            log.info("base62 encode result:" + encodeStr);
        return encodeStr;
    }


    public String urlHashing(String originalString){
        String sha256hex = Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();

        return sha256hex.substring(0, 10);
    }
}
