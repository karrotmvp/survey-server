package com.daangn.survey.third;

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
