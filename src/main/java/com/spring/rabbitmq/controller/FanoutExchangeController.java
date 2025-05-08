package com.spring.rabbitmq.controller;

import com.spring.rabbitmq.model.MessageDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/fanout-exchange")
public class FanoutExchangeController {

    @Autowired
    AmqpTemplate fanoutQueue;

    @GetMapping("/message")
    public ResponseEntity<?> sendMessage() {
         fanoutQueue.convertAndSend(MessageDTO.builder()
                         .status("fanoutMessage")
                         .dateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                 .build());
         return ResponseEntity.ok("Message Sent Successfully using fanout exchange!!!!!!");
    }
}
