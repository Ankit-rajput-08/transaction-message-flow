package com.jpmc.midascore.service;

import com.jpmc.midascore.dto.TransactionDTO;
import com.jpmc.midascore.entity.Balance;
import com.jpmc.midascore.entity.TransactionEntity;
import com.jpmc.midascore.entity.UserEntity;
import com.jpmc.midascore.repository.BalanceRepository;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class TransactionService {

    private final UserRepository userRepo;
    private final BalanceRepository balanceRepo;
    private final TransactionRepository transactionRepo;

    public TransactionService(UserRepository userRepo,
                              BalanceRepository balanceRepo,
                              TransactionRepository transactionRepo) {
        this.userRepo = userRepo;
        this.balanceRepo = balanceRepo;
        this.transactionRepo = transactionRepo;
    }


    @Transactional
    public Balance processTransaction(TransactionDTO request) {

        UserEntity sender = userRepo.findById(request.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        UserEntity recipient = null;
        if (request.getRecipientId() != null) {
            recipient = userRepo.findById(request.getRecipientId())
                    .orElseThrow(() -> new RuntimeException("Recipient not found"));
        }

        Balance senderBalance = balanceRepo.findById(sender.getId())
                .orElse(new Balance(sender, 0));

        switch (request.getType().toUpperCase()) {

            case "CREDIT":
                senderBalance.setAmount(
                        senderBalance.getAmount() + request.getAmount()
                );
                break;

            case "DEBIT":
                if (senderBalance.getAmount() < request.getAmount()) {
                    throw new RuntimeException(" Insufficient Balance!");
                }
                senderBalance.setAmount(
                        senderBalance.getAmount() - request.getAmount()
                );
                break;

            default:
                throw new RuntimeException("Invalid transaction type");
        }

        balanceRepo.save(senderBalance);

        TransactionEntity transaction = new TransactionEntity();
        transaction.setSender(sender);
        transaction.setRecipient(recipient);
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepo.save(transaction);

        return senderBalance;
    }


    public float getBalance(Long userId) {

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return balanceRepo.findById(user.getId())
                .orElse(new Balance(user, 0))
                .getAmount();
    }

    public List<TransactionEntity> getTransactionHistory(Long userId) {
        return transactionRepo.findBySenderIdOrRecipientId(userId, userId);
    }
}
