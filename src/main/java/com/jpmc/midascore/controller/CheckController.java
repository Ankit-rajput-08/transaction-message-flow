package com.jpmc.midascore.controller;

import com.jpmc.midascore.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
@RequiredArgsConstructor
public class CheckController {


    private  final KafkaService producer;

    @GetMapping("/test")
    public String testKafka() {
        producer.sendMessage("Hello from Spring Boot - Kafka Test");
        return "Message Sent Successfully!";
    }
}
