package com.zele.zelcoinsb.controller;

import com.zele.zelcoinsb.models.dtos.transaction.TransactionCreateRequest;
import com.zele.zelcoinsb.models.dtos.transaction.TransactionViewDTO;
import com.zele.zelcoinsb.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor
public class TransactionController {
    private TransactionService transactionService;

    @GetMapping("/all")
    public List<TransactionViewDTO> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{tx_id}")
    public ResponseEntity<TransactionViewDTO> getTransactionById(@PathVariable Long tx_id) {
        return transactionService.getTransactionById(tx_id);
    }

    @PostMapping("/new")
    public ResponseEntity<TransactionViewDTO> createTransaction(
            @RequestBody TransactionCreateRequest createRequest) {
        return transactionService.createTransaction(createRequest);
    }
}
