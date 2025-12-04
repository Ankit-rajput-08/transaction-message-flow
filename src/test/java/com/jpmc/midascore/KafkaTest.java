
package com.jpmc.midascore;

import com.jpmc.midascore.dto.TransactionDTO;
import com.jpmc.midascore.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
public class KafkaTest {
    @Autowired
    private KafkaTemplate<String, TransactionDTO> kafkatemplate;

    @Autowired
    private TransactionService transactionService;

    @Test
    void kafkaConnectiontest(){
        TransactionDTO test = TransactionDTO.builder()
                .type("CREDIT").amount(12.45f).senderId(1L).recipientId(2L)
                .build();
        kafkatemplate.send("topic_0",test);
    }

}
