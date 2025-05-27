package com.zele.zelcoinsb.service;

import com.zele.zelcoinsb.exceptions.transaction.TransactionNotFoundException;
import com.zele.zelcoinsb.exceptions.wallet.WalletNotFoundException;
import com.zele.zelcoinsb.mapper.TransactionMapper;
import com.zele.zelcoinsb.models.dtos.transaction.TransactionCreateRequest;
import com.zele.zelcoinsb.models.dtos.transaction.TransactionViewDTO;
import com.zele.zelcoinsb.models.entities.Transaction;
import com.zele.zelcoinsb.repository.TransactionRepository;
import com.zele.zelcoinsb.repository.WalletRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransactionService {
    private final LedgerService ledgerService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final WalletService walletService;
    private final WalletRepository walletRepository;

    public TransactionService(LedgerService ledgerService, TransactionRepository transactionRepository, TransactionMapper transactionMapper, @Lazy WalletService walletService, WalletRepository walletRepository) {
        this.ledgerService = ledgerService;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.walletService = walletService;
        this.walletRepository = walletRepository;
    }

    public List<TransactionViewDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toTransactionViewDTO)
                .toList();
    }

    public ResponseEntity<TransactionViewDTO> getTransactionById(Long txId) {
        var transaction = transactionRepository.findById(txId).orElse(null);
        checkTransactionInDB(transaction);
        return ResponseEntity.status(HttpStatus.OK).body(transactionMapper.toTransactionViewDTO(transaction));
    }


    public ResponseEntity<TransactionViewDTO> createTransaction(TransactionCreateRequest createRequest) {
        var senderWallet = walletRepository.findById(createRequest.getSender()).orElse(null);
        var receiverWallet = walletRepository.findById(createRequest.getReceiver()).orElse(null);
        if (senderWallet == null || receiverWallet == null)
            throw new WalletNotFoundException("Sender or receiver does not exist");
        return walletService.transact(senderWallet.getPublicKey(), receiverWallet.getPublicKey(), createRequest.getAmount());
    }

    private void checkTransactionInDB(Transaction transaction) {
        if (transaction == null) throw new TransactionNotFoundException("Transaction not found");
    }

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
