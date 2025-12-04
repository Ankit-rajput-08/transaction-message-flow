package com.jpmc.midascore.controller;

import com.jpmc.midascore.dto.TransactionDTO;
import com.jpmc.midascore.entity.TransactionEntity;
import com.jpmc.midascore.entity.UserEntity;
import com.jpmc.midascore.service.KafkaService;
import com.jpmc.midascore.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final KafkaService kafkaService;



    @PostMapping
    public ResponseEntity<String> addTransaction(@RequestBody TransactionDTO request) {
        kafkaService.sendTransaction(request);
        return ResponseEntity.ok("Transaction queued successfully");
    }


    @GetMapping("/balance/{userId}")
    public ResponseEntity<Float> getBalance(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getBalance(userId));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<TransactionEntity>> getUserHistory(@PathVariable Long userId) {
        List<TransactionEntity> history = transactionService.getTransactionHistory(userId);
        return ResponseEntity.ok(history);
    }

}
