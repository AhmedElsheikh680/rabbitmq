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
public class FanoutExchangeConfig {

    @Autowired
    private AmqpAdmin amqpAdmin;
    @Value("${rabbit.fanout.queue1}")
    private String queue1;
    @Value("${rabbit.fanout.queue2}")
    private String queue2;
    @Value("${rabbit.fanout.queue3}")
    private String queue3;
    @Value("${rabbit.fanout.exchabge}")
    private String fanoutExchange;

    @Bean
    Queue createFanoutQueue1() {
        return new Queue(queue1, true, false, false);
    }

    @Bean
    Queue createFanoutQueue2() {
        return new Queue(queue2, true, false, false);
    }

    @Bean
    Queue createFanoutQueue3() {
        return new Queue(queue3, true, false, false);
    }

    @Bean
    Binding createFanoutBinding1 () {
        return BindingBuilder.bind(createFanoutQueue1()).to(createFanoutExchange());
    }

    @Bean
    Binding createFanoutBinding2 () {
        return BindingBuilder.bind(createFanoutQueue2()).to(createFanoutExchange());
    }

    @Bean
    Binding createFanoutBinding3 () {
        return BindingBuilder.bind(createFanoutQueue3()).to(createFanoutExchange());
    }

    @Bean
    FanoutExchange createFanoutExchange() {
        return new FanoutExchange(fanoutExchange, true, false);
    }

    @Bean
    AmqpTemplate fanoutQueue(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setExchange(fanoutExchange);

        return rabbitTemplate;
    }
    @PostConstruct
    public void init () {
        amqpAdmin.declareQueue(createFanoutQueue1());
        amqpAdmin.declareQueue(createFanoutQueue2());
        amqpAdmin.declareQueue(createFanoutQueue3());

        amqpAdmin.declareExchange(createFanoutExchange());

        amqpAdmin.declareBinding(createFanoutBinding1());
        amqpAdmin.declareBinding(createFanoutBinding2());
        amqpAdmin.declareBinding(createFanoutBinding3());
    }

}
