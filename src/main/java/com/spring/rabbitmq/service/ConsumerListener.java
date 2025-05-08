package com.spring.rabbitmq.service;

import com.spring.rabbitmq.model.MessageDTO;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerListener {

    @Autowired
    private AmqpTemplate directQueue;
    private static final String HEADER_X_RETRY = "x-retry";
    private static final int MAX_RETRY = 3;


    @RabbitListener(queues = {"${rabbit.direct.queue1}", "${rabbit.topic.queue1}"}, containerFactory = "simpleRabbitListenerContainerFactory")
    public void receiveMessage(@Payload MessageDTO message, Message msg) {
        try {

            System.out.println(message.toString());
            throw new RuntimeException();
        } catch (Exception e) {
            Integer count = (Integer) msg.getMessageProperties().getHeaders().get(HEADER_X_RETRY);
            if (count == null) {
                count=0;
            } else if (count >=MAX_RETRY) {
                System.out.println("Message Ignore!!!!!!!!!!!!!!!!!!!!!!");
                return;
            }
            System.out.println("Message Retry!!!!!!!!!!!!!!!!");
            msg.getMessageProperties().getHeaders().put(HEADER_X_RETRY, ++count);
            directQueue.sendAndReceive(msg.getMessageProperties().getReceivedRoutingKey(), msg);

        }

    }
}
