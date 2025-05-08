package com.spring.rabbitmq.service;

import com.spring.rabbitmq.model.MessageDTO;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ConsumeService {

    @Autowired
    private AmqpAdmin amqpAdmin;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public int getCountMessage(String queueName) {
       Properties properties =  amqpAdmin.getQueueProperties(queueName);
       return (int) properties.get(RabbitAdmin.QUEUE_MESSAGE_COUNT);
    }

    public List<MessageDTO> receiveMessage(String queueName) {
        int count = getCountMessage(queueName);
        return IntStream.range(0, count)
                .mapToObj(value -> (MessageDTO)rabbitTemplate.receiveAndConvert(queueName))
                .collect(Collectors.toList());
    }
}
