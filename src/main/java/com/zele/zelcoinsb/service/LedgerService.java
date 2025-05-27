package com.zele.zelcoinsb.service;

import com.zele.zelcoinsb.models.entities.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
@AllArgsConstructor
public class LedgerService {
    private final List<Transaction> transactions;

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
