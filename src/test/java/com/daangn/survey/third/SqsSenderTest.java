package com.daangn.survey.third;

import com.daangn.survey.common.util.csv.CsvUtils;
import com.daangn.survey.third.messaging.sqs.ChatMessage;
import com.daangn.survey.third.messaging.sqs.SqsSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.List;

@SpringBootTest
public class SqsSenderTest {

    @Autowired
    private SqsSender sqsSender;

    @Test
    public void sqsSendTest() throws JsonProcessingException {
        List<List<String>> csv = CsvUtils.readToList("/Users/allen/Desktop/businessId_test.csv");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ChatMessage.Receiver receiver = new ChatMessage.Receiver(0, "BUSINESS_ACCOUNT");
        ChatMessage message = ChatMessage.builder()
                .receiver(receiver)
                .title("사장님을 위한 설문 서비스, 무따에요!")
                .content("매장에 대한 의견이 궁금할 때, 무따 서비스로 설문을 작성하고, 우리 동네 이웃의 의견을 들어보세요!\n('무따'는 당근마켓 MVP인턴십 프로그램에서 한시적으로 운영하는 서비스에요. 이용에 참고해주세요.)")
                .image("https://survey-asset-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail.png")
                .build();

        for(int i = 1 ; i < 5 ; i++){
            message.getReceiver().setId(Integer.parseInt(csv.get(i).get(0)));
            sqsSender.sendMessage(message);
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    @Test
    @Disabled
    public void csv읽기(){
        List<List<String>> csv = CsvUtils.readToList("/Users/allen/Desktop/businessId.csv");

        System.out.println(csv);
    }
}
