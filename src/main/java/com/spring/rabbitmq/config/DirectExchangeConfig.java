package com.spring.rabbitmq.config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectExchangeConfig {

    @Autowired
    private AmqpAdmin amqpAdmin;
    @Value("${rabbit.direct.queue1}")
    private String queue1;
    @Value("${rabbit.direct.queue2}")
    private String queue2;
    @Value("${rabbit.direct.queue3}")
    private String queue3;
    @Value("${rabbit.direct.deadLetter.queue4}")
    private String deadLetterQueue4;
    @Value("${rabbit.direct.binding1}")
    private String binding1;
    @Value("${rabbit.direct.binding2}")
    private String binding2;
    @Value("${rabbit.direct.binding3}")
    private String binding3;
    @Value("${rabbit.direct.exchange}")
    private String directExchange;

    @Bean
    Queue createDirectQueue1() {

//        return new Queue(queue1, true, false, false);
        return QueueBuilder.durable(queue1)
                .deadLetterExchange("") //default
                .deadLetterRoutingKey(deadLetterQueue4)
                .build();
    }

    @Bean
    Queue createDirectQueue2() {
        return new Queue(queue2, true, false, false);
    }

    @Bean
    Queue createDirectQueue3() {
        return new Queue(queue3, true, false, false);
    }

    @Bean
    Queue createDeadLetterQueue4() {
        return new Queue(deadLetterQueue4, true, false, false);
    }

    @Bean
    DirectExchange createDirectExchange() {
        return new DirectExchange(directExchange, true, false);
    }

    @Bean
    Binding createBinding1() {
        return BindingBuilder.bind(createDirectQueue1()).to(createDirectExchange()).with(binding1);
    }

    @Bean
    Binding createBinding2() {
        return BindingBuilder.bind(createDirectQueue2()).to(createDirectExchange()).with(binding2);
    }

    @Bean
    Binding createBinding3() {
        return BindingBuilder.bind(createDirectQueue3()).to(createDirectExchange()).with(binding3);
    }


    @Bean
    AmqpTemplate directQueue(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(directExchange);

        return rabbitTemplate;
    }

@PostConstruct
    public void init() {
        amqpAdmin.declareQueue(createDirectQueue1());
        amqpAdmin.declareQueue(createDirectQueue2());
        amqpAdmin.declareQueue(createDirectQueue3());

        amqpAdmin.declareQueue(createDeadLetterQueue4());

        amqpAdmin.declareExchange(createDirectExchange());

        amqpAdmin.declareBinding(createBinding1());
        amqpAdmin.declareBinding(createBinding2());
        amqpAdmin.declareBinding(createBinding3());
}




}
