package com.daangn.survey.third;

import com.daangn.survey.common.util.csv.CsvUtils;
import com.daangn.survey.third.messaging.sqs.ChatMessage;
import com.daangn.survey.third.messaging.sqs.SqsSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SqsSenderTest {

    @Autowired
    private SqsSender sqsSender;

    @Test
    public void sqsSendTest() throws JsonProcessingException {
        List<List<String>> csv = CsvUtils.readToList("/Users/allen/Desktop/businessId.csv");

        ChatMessage.Receiver receiver = new ChatMessage.Receiver(1272507, "BUSINESS_ACCOUNT");
        ChatMessage message = ChatMessage.builder()
                .receiver(receiver)
                .title("고객 설문 서비스")
                .content("사장님 안녕하세요!\n설문을 작성하고, 우리 동네 이웃의 의견을 들어보세요!")
                .image("https://survey-asset-bucket.s3.ap-northeast-2.amazonaws.com/chatting-image.png")
                .build();

        for(int i = 0 ; i < csv.size() / 100; i++){
            message.setTitle("test" + csv.get(i).get(0));
            sqsSender.sendMessage(message);
        }
    }

    @Test
    public void csv읽기(){
        List<List<String>> csv = CsvUtils.readToList("/Users/allen/Desktop/businessId.csv");

        System.out.println(csv);
    }
}
