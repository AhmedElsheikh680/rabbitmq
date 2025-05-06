package com.spring.rabbitmq.controller;


import com.spring.rabbitmq.model.Message;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/topic-exchange")
public class TopicExchangeController {

    @Autowired
    private AmqpTemplate topicTemplate;

    @GetMapping("/message/{pattern}")
    public ResponseEntity<?> sendMessage(@PathVariable String pattern) {
        topicTemplate.convertAndSend(pattern, Message.builder()
                        .status("topicExchange")
                        .dateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build());

        return ResponseEntity.ok("Message Sent Successfully using topic exchange!!!!!");
    }
}
