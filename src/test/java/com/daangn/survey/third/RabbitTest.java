package com.daangn.survey.third;

import com.daangn.survey.core.config.RabbitConfig;
import com.daangn.survey.third.rabbitmq.DefaultListener;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
//public class RabbitTest {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    private DefaultListener defaultListener;
//
//    @Test
//    public void messageWithRabbit(){
//        rabbitTemplate.convertAndSend(RabbitConfig.topicExchangeName, "default.test", "hello RabbitMq");
//        String text = (String) rabbitTemplate.receiveAndConvert(RabbitConfig.queueName);
//        System.out.println(text);
//    }
//}
