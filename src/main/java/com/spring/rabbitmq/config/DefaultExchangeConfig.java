package com.spring.rabbitmq.config;


import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultExchangeConfig {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Value("${rabbit-default-queue}")
    private String defaultQueue;

    @Bean
    Queue createQueue() {
//        return new Queue("default-queue", true, false, false);
        return new Queue(defaultQueue, true, false, false);
    }


    @Bean
    Queue createQueue2() {
        return new Queue(defaultQueue+1, true, false, false);
    }
    @Bean
    AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
//        rabbitTemplate.setRoutingKey("default-queue");
        rabbitTemplate.setRoutingKey(defaultQueue);

        return rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        amqpAdmin.declareQueue(createQueue());
        amqpAdmin.declareQueue(createQueue2());
    }


}
