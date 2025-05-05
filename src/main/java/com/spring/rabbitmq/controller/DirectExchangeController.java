package com.spring.rabbitmq.controller;

import com.spring.rabbitmq.model.Message;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/direct-exchange")
public class DirectExchangeController {

    @Autowired
    private AmqpTemplate directQueue;
    @Value("${rabbit.direct.binding1}")
    private String binding1;
    @Value("${rabbit.direct.binding2}")
    private String binding2;
    @Value("${rabbit.direct.binding3}")
    private String binding3;


    @GetMapping("/message/{num}")
    public ResponseEntity<?> sendMessage(@PathVariable Integer num) throws Exception { // 1,3,3
        String key;
        switch (num) {
            case 1:
                key = binding1;
                break;
            case 2:
                key = binding2;
                break;
            case 3:
                key = binding3;
                break;
            default:
                throw new Exception("Invalid Number!!!!!!!!");
        }
        Message message = Message.builder()
                .status("directMessage")
                .dateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
        directQueue.convertAndSend(key, message);
        return ResponseEntity.ok("Message Sent Successfully!!!!!!!");
//        return "Message Sent Successfully!!!!!!!";
    }
}
