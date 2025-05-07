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
public class HeaderExchangeConfig {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Value("${rabbit.header.queue1}")
    private String queue1;
    @Value("${rabbit.header.queue2}")
    private String queue2;
    @Value("${rabbit.header.queue3}")
    private String queue3;
    @Value("${rabbir.header.exchange}")
    private String headerExchange;

    @Bean
    Queue createHeaderQueue1() {
        return new Queue(queue1, true, false, false);
    }

    @Bean
    Queue createHeaderQueue2() {
        return new Queue(queue2, true, false, false);
    }

    @Bean
    Queue createHeaderQueue3() {
        return new Queue(queue3, true, false,false);
    }
    @Bean
    Binding createHeaderBinding1() {
        return BindingBuilder.bind(createHeaderQueue1()).to(createHeaderExchange()).whereAny("error", "warning").exist();
    }

    @Bean
    Binding createHeaderBinding2() {
        return BindingBuilder.bind(createHeaderQueue2()).to(createHeaderExchange()).whereAny("warning", "debug").exist();
    }

    @Bean
    Binding createHeaderBinding3() {
        return BindingBuilder.bind(createHeaderQueue3()).to(createHeaderExchange()).whereAll("error", "warning", "debug").exist();
    }

    @Bean
    HeadersExchange createHeaderExchange() {
        return new HeadersExchange(headerExchange, true, false);
    }

    @Bean
    AmqpTemplate headerQueue(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(headerExchange);

        return rabbitTemplate;

    }

    @PostConstruct
    public void init() {
        amqpAdmin.declareQueue(createHeaderQueue1());
        amqpAdmin.declareQueue(createHeaderQueue2());
        amqpAdmin.declareQueue(createHeaderQueue3());
        amqpAdmin.declareExchange(createHeaderExchange());
        amqpAdmin.declareBinding(createHeaderBinding1());
        amqpAdmin.declareBinding(createHeaderBinding2());
        amqpAdmin.declareBinding(createHeaderBinding3());

    }

}
