package com.spring.rabbitmq.controller;

import com.spring.rabbitmq.model.MessageDTO;
import com.spring.rabbitmq.service.ConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consume")
public class ConsumeController {

    @Autowired
    private ConsumeService consumeService;

    @GetMapping("/message/{queueName}")
    public ResponseEntity<List<MessageDTO>> consumeMessage(@PathVariable String queueName) {
        return ResponseEntity.ok(consumeService.receiveMessage(queueName));
    }


}
