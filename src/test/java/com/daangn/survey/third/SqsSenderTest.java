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

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ChatMessage.Sender sender = new ChatMessage.Sender(1271155, "BUSINESS_ACCOUNT");
        ChatMessage message = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .title("사장님이 작성해주신 설문에 답변이 도착했어요!")
                .content("안녕하세요, 사장님!\n" +
                        "\n" +
                        "동네 이웃의 의견을 물어볼 수 있는 서비스, '무따'로 설문을 제작하신 것 기억하시나요?\n" +
                        "\n" +
                        "이웃분들이 정성껏 답변을 작성해주셨어요! \n" +
                        "\n" +
                        "지금 답변을 확인해보세요!")
                .image("https://survey-asset-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail.png")
                .build();

        for(int i = 1 ; i < csv.size() ; i++){
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

    @Test
    @Disabled
    void sendBatchMessage() throws JsonProcessingException {
        List<List<String>> csv = CsvUtils.readToList("/Users/allen/Desktop/businessId_test.csv");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<ChatMessage> messages = new LinkedList<>();

        for(int i = 1 ; i <= 10 ; i++){

            ChatMessage.Receiver receiver = new ChatMessage.Receiver(Integer.parseInt(csv.get(i).get(0)), "BUSINESS_ACCOUNT");
            ChatMessage message = ChatMessage.builder()
                    .receiver(receiver)
                    .title("사장님을 위한 설문 서비스, 무따에요!")
                    .content("매장에 대한 의견이 궁금할 때, 무따 서비스로 설문을 작성하고, 우리 동네 이웃의 의견을 들어보세요!\n('무따'는 당근마켓 MVP인턴십 프로그램에서 한시적으로 운영하는 서비스에요. 이용에 참고해주세요.)")
                    .image("https://survey-asset-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail.png")
                    .build();

            messages.add(message);
        }

        sqsSender.sendBatchMessage(messages);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
