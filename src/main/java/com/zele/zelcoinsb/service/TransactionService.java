package com.zele.zelcoinsb.service;

import com.zele.zelcoinsb.models.entities.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class TransactionService {
    private final LedgerService ledgerService;

    public Transaction createTransaction(Double amount, PublicKey sender, PublicKey receiverKey) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setSender(sender);
        transaction.setReceiver(receiverKey);
        transaction.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        ledgerService.addTransaction(transaction);
        return transaction;
    }
}
