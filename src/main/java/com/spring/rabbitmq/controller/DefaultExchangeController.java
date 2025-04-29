package com.spring.rabbitmq.controller;


import com.spring.rabbitmq.model.Message;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/default-exchange")
public class DefaultExchangeController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/message")
    public void sendMessage() {
        amqpTemplate.convertAndSend(
                Message.builder()
                        .status("default")
                        .dateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build());
    }
}
