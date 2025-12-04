package com.jpmc.midascore.service;

import com.jpmc.midascore.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.ArrayList;
import java.util.List;

public class KafkaConsumerService {

    @Autowired
    private TransactionService transactionService;
    private final List<TransactionDTO> transactionDTOList = new ArrayList<>();

    @KafkaListener(
            topics = "topic_0",
            groupId = "midas-consumer",
            containerFactory = "transactionKafkaListenerContainerFactory"
    )
    public void consumeMessage(TransactionDTO dto) {
        System.out.println("Received from Kafka: " + dto);
        transactionDTOList.add(dto);              // <-- VERY IMPORTANT

        transactionService.processTransaction(dto);
    }

    public List<TransactionDTO> transactionDTOList(){
        return transactionDTOList ;
    }
}
