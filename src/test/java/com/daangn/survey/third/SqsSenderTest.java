package com.daangn.survey.third;

import com.amazonaws.services.sqs.model.Message;
import com.daangn.survey.common.util.csv.CsvUtils;
import com.daangn.survey.third.karrot.chatting.UserChatMessage;
import com.daangn.survey.third.messaging.sqs.BizChatMessage;
import com.daangn.survey.third.messaging.sqs.SqsSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class SqsSenderTest {

    @Autowired
    private SqsSender sqsSender;

    @Test
    @Disabled
    public void sqsSendTest() throws JsonProcessingException {
        List<List<String>> csv = CsvUtils.readToList("/Users/allen/Desktop/businessId_test.csv");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        BizChatMessage.Receiver receiver = new BizChatMessage.Receiver(0, "BUSINESS_ACCOUNT");
        BizChatMessage.Sender sender = new BizChatMessage.Sender(1271155, "BUSINESS_ACCOUNT");
        BizChatMessage message = BizChatMessage.builder()
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
    void sendBatchBizChatMessage() throws JsonProcessingException {
        List<List<String>> csv = CsvUtils.readToList("/Users/allen/Desktop/businessId_test.csv");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Message> messages = new LinkedList<>();

        for(int i = 1 ; i < csv.size() ; i++){
            BizChatMessage.Sender sender = new BizChatMessage.Sender(1271155, "BUSINESS_ACCOUNT");
            BizChatMessage.Receiver receiver = new BizChatMessage.Receiver(Integer.parseInt(csv.get(i).get(0)), "BUSINESS_ACCOUNT");
            BizChatMessage message = BizChatMessage.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .title("사장님을 위한 설문 서비스, 무따가 정식 오픈했어요!")
                    .content("매장에 대한 우리 동네 이웃 의견이 궁금할 때, 무따 서비스로 설문을 작성하고, 비즈프로필 소식이나 SNS에 공유해보세요!\n\n 무따로 우리 동네 이웃의 의견을 들어보세요.")
                    .image("https://survey-asset-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail.png")
                    .build();

            messages.add(message);

            if(i % 10 == 0) {
                sqsSender.sendBatchMessage(messages);
                messages.clear();
            }
        }
        sqsSender.sendBatchMessage(messages);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    @Test
    @Disabled
    void sendBatchUserChatMessage() throws JsonProcessingException {
        List<List<String>> csv = CsvUtils.readToList("/Users/allen/Desktop/allen_test.csv");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Message> messages = new LinkedList<>();

        for(int i = 1 ; i < csv.size() ; i++){
            UserChatMessage.InputMessage.Action.Payload payload = new UserChatMessage.InputMessage.Action.Payload("설문하러 가기", "towneers://web/ad/user_surveys/5615'");
            UserChatMessage.InputMessage.Action action = new UserChatMessage.InputMessage.Action(payload, "PRIMARY_BUTTON");
            UserChatMessage.InputMessage inputMessage = new UserChatMessage.InputMessage(Arrays.asList(action), csv.get(i).get(0), "사장님 설문에 참여해주신 여러분 이야기를 듣고 싶어요!",
                    "몇일 전, 동네 사장님이 남긴 설문에 답변해주신 분들 대상으로 간단한 설문조사를 진행하고 있어요.\n" +
                    "설문에 응해주시면, 추첨을 통해 스타벅스 기프티콘을 드려요!", "https://survey-asset-bucket.s3.ap-northeast-2.amazonaws.com/thumbnail.png");
            UserChatMessage userChatMessage = new UserChatMessage(inputMessage);

            messages.add(userChatMessage);

            if(i % 10 == 0) {
                sqsSender.sendBatchMessage(messages);
                messages.clear();
            }
        }
        sqsSender.sendBatchMessage(messages);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    @Test
    @Disabled
    void errorMessage() throws JsonProcessingException {
        List<List<String>> csv = CsvUtils.readToList("/Users/allen/Desktop/allen_test.csv");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Message> messages = new LinkedList<>();

        for(int i = 1 ; i < csv.size() ; i++){
            UserChatMessage.InputMessage inputMessage = new UserChatMessage.InputMessage(Arrays.asList(), csv.get(i).get(0), "무따 서비스에서 사죄의 말씀 드립니다",
                    "무따 서비스 잘못으로 인해 똑같은 채팅을 여러 번 보내게 되었어요.\n\n불편함을 느끼게 해드려서 죄송합니다.\n\n더 나은 서비스가 될 수 있도록 노력하겠습니다.\n\n죄송합니다.","");
            UserChatMessage userChatMessage = new UserChatMessage(inputMessage);

            messages.add(userChatMessage);

            if(i % 10 == 0) {
                sqsSender.sendBatchMessage(messages);
                messages.clear();
            }
        }
        sqsSender.sendBatchMessage(messages);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
