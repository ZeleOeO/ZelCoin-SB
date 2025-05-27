package com.zele.zelcoinsb.service;

import com.zele.zelcoinsb.models.entities.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.PublicKey;

@Service
@AllArgsConstructor
public class TransactionService {
    public Transaction createTransaction(Double amount, PublicKey sender, PublicKey receiverKey) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setSender(sender);
        transaction.setReceiver(receiverKey);
        return transaction;
    }
}
