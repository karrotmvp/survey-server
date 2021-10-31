package com.daangn.survey.common.util.shorturl.component;

import com.google.common.hash.Hashing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.charset.Charset;


@ExtendWith(SpringExtension.class)
class UrlEncoderTest {

    private final int BASE62 = 62;
    private final String BASE62_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Test
    public void 인코딩(){
        long param = 1234;

        StringBuffer sb = new StringBuffer();
        while(param > 0) {
            sb.append(BASE62_CHAR.charAt((int) (param % BASE62)));
            param /= BASE62;
        }
        System.out.println(sb.toString());

    }

    @Test
    public void 디코딩(){
        String param = "4T";

        long sum = 0;
        long power = 1;
        for (int i = 0; i < param.length(); i++) {
            sum += BASE62_CHAR.indexOf(param.charAt(i)) * power;
            power *= BASE62;
        }

        System.out.println(sum);
    }
}