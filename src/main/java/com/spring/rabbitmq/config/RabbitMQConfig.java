package com.spring.rabbitmq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        AbstractConnectionFactory abstractConnectionFactory = new CachingConnectionFactory();
        abstractConnectionFactory.setHost(host);
        abstractConnectionFactory.setPort(port);
        abstractConnectionFactory.setVirtualHost(virtualHost);
        abstractConnectionFactory.setUsername(username);
        abstractConnectionFactory.setPassword(password);

        return abstractConnectionFactory;
    }
    // convert message that come form rabbitmq to project to json
    @Bean

    public MessageConverter messageConverter () {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(messageConverter());
        factory.setMaxConcurrentConsumers(20);
        factory.setConcurrentConsumers(10);
        factory.setPrefetchCount(5);
        factory.setAutoStartup(true);
        factory.setDefaultRequeueRejected(false);
//        factory.setAdviceChain(RetryInterceptorBuilder.stateless().maxAttempts(3).recoverer(new RejectAndDontRequeueRecoverer()).build());

        return factory;
    }

}
