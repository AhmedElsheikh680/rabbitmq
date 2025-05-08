package com.spring.rabbitmq.controller;

import com.spring.rabbitmq.model.MessageDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/header-exchange")
public class HeaderExchangeController {

    @Autowired
    private AmqpTemplate headerQueue;

    @GetMapping("/message")
    public ResponseEntity<?> sendMessage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "warning", required = false) String warning,
            @RequestParam(value = "debug", required = false) String debug
    ) {
        MessageDTO message = MessageDTO.builder()
                .status("headerMessage")
                .dateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        MessageBuilder messageBuilder = MessageBuilder.withBody(message.toString().getBytes());
        if (error != null) {
            messageBuilder.setHeader("error", error);
        }
        if (debug != null) {
            messageBuilder.setHeader("debug", debug);
        }
        if (warning != null) {
            messageBuilder.setHeader("warning", warning);

        }
        headerQueue.convertAndSend(messageBuilder.build());
        return ResponseEntity.ok("Message Sent Successfully using header exchange!!!!!!");
    }
}
