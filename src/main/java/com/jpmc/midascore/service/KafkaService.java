package com.jpmc.midascore.service;

import com.jpmc.midascore.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {


    @Autowired
    private KafkaTemplate<String, TransactionDTO> kafkaTemplate;


    public void sendMessage(String message) {
        TransactionDTO test = TransactionDTO.builder()
                .type("test")
                .build();

        kafkaTemplate.send("topic_0",test );
        System.out.println("Message sent to Kafka: " + message);
    }

    public void sendTransaction(TransactionDTO dto) {
        kafkaTemplate.send("topic_0", dto);
        System.out.println("Transaction sent: " + dto);
    }
}
