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
public class TopicExchangeConfig {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Value("${rabbit.topic.queue1}")
    private String queue1;
    @Value("${rabbit.topic.queue2}")
    private String queue2;
    @Value("${rabbit.topic.queue3}")
    private String queue3;
    @Value("${rabbit.topic.pattern1}")
    private String pattern1;
    @Value("${rabbit.topic.pattern2}")
    private String pattern2;
    @Value("${rabbit.topic.pattern3}")
    private String pattern3;
    @Value("${rabbit.topic.exchange}")
    private String topicExchange;

    @Bean
    Queue createTopicQueue1() {
        return new Queue(queue1, true, false, false);
    }

    @Bean
    Queue createTopicQueue2() {
        return new Queue(queue2, true, false, false);
    }

    @Bean
    Queue createTopicQueue3() {
        return new Queue(queue3, true, false, false);
    }

    @Bean
    TopicExchange createTopicExchange() {
        return new TopicExchange(topicExchange, true, false);
    }

    @Bean
    Binding createBindingPattern1() {
        return BindingBuilder.bind(createTopicQueue1()).to(createTopicExchange()).with(pattern1);
    }

    @Bean
    Binding createBindingPattern2() {
        return BindingBuilder.bind(createTopicQueue2()).to(createTopicExchange()).with(pattern2);
    }

    @Bean
    Binding createBindingPattern3() {
        return BindingBuilder.bind(createTopicQueue3()).to(createTopicExchange()).with(pattern3);
    }

    @Bean
    AmqpTemplate topicTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(topicExchange);
        return rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        amqpAdmin.declareQueue(createTopicQueue1());
        amqpAdmin.declareQueue(createTopicQueue2());
        amqpAdmin.declareQueue(createTopicQueue3());

        amqpAdmin.declareExchange(createTopicExchange());

        amqpAdmin.declareBinding(createBindingPattern1());
        amqpAdmin.declareBinding(createBindingPattern2());
        amqpAdmin.declareBinding(createBindingPattern3());
    }


























}
