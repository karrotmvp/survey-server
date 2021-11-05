package com.daangn.survey.third.messaging.rabbitmq;

import com.daangn.survey.core.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.util.LinkedHashMap;

//@Component
@RabbitListener(queues = RabbitConfig.queueName)
public class DefaultListener {

    @RabbitHandler
    public void receiveMessage(LinkedHashMap message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            System.out.println(message);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}