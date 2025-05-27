package com.zele.zelcoinsb.service;

import com.zele.zelcoinsb.exceptions.transaction.TransactionErrorException;
import com.zele.zelcoinsb.exceptions.wallet.WalletInsufficientFundsException;
import com.zele.zelcoinsb.exceptions.wallet.WalletNotFoundException;
import com.zele.zelcoinsb.exceptions.wallet.WalletSignatureErrorException;
import com.zele.zelcoinsb.mapper.TransactionMapper;
import com.zele.zelcoinsb.mapper.WalletMapper;
import com.zele.zelcoinsb.models.dtos.transaction.TransactionViewDTO;
import com.zele.zelcoinsb.models.dtos.wallet.WalletViewDTO;
import com.zele.zelcoinsb.models.entities.Transaction;
import com.zele.zelcoinsb.models.entities.Wallet;
import com.zele.zelcoinsb.repository.TransactionRepository;
import com.zele.zelcoinsb.repository.WalletRepository;
import com.zele.zelcoinsb.tools.CustomKeyPairGenerator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class WalletService {
    private final BlockChainService blockChainService;
    private final TransactionService transactionService;
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;


    public WalletService(BlockChainService blockChainService, @Lazy TransactionService transactionService, WalletRepository walletRepository, WalletMapper walletMapper, TransactionMapper transactionMapper, TransactionRepository transactionRepository) {
        this.blockChainService = blockChainService;
        this.transactionService = transactionService;
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
        this.transactionMapper = transactionMapper;
        this.transactionRepository = transactionRepository;
    }

    public List<WalletViewDTO> getAllWallets() {
        return walletRepository.findAll()
                .stream()
                .map(walletMapper::toWalletViewDTO)
                .toList();
    }

    public ResponseEntity<WalletViewDTO> getWalletById(Long wallet_id) {
        var wallet = walletRepository.findById(wallet_id).orElse(null);
        checkWalletInDB(wallet);
        return ResponseEntity.status(HttpStatus.OK).body(walletMapper.toWalletViewDTO(wallet));
    }

//    public ResponseEntity<WalletViewDTO> getWalletByWalletAddress(PublicKey publicKey) {
//        var wallet = walletRepository.findByPublicKey(publicKey).orElse(null);
//        checkWalletInDB(wallet);
//        return ResponseEntity.status(HttpStatus.OK).body(walletMapper.toWalletViewDTO(wallet));
//    }

    public ResponseEntity<WalletViewDTO> createWalletController() {
        Wallet wallet = createWallet();
        walletRepository.save(wallet);
        return ResponseEntity.status(HttpStatus.CREATED).body(walletMapper.toWalletViewDTO(wallet));
    }

     public ResponseEntity<TransactionViewDTO> transact(PublicKey sender, PublicKey receiver, Double amount) {
        var senderWallet = walletRepository.findByPublicKey(sender).orElse(null);
        var receiverWallet = walletRepository.findByPublicKey(receiver).orElse(null);
        checkWalletInDB(senderWallet);
        checkWalletInDB(receiverWallet);
        if (senderWallet.getPublicKey().equals(receiverWallet.getPublicKey())) throw new TransactionErrorException("Cannot transact with same sender and receiver");
        Transaction transaction = sendMoney(amount, senderWallet, receiver);
        senderWallet.setBalance(senderWallet.getBalance() - amount);
        receiverWallet.setBalance(receiverWallet.getBalance() + amount);
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);
        transactionRepository.save(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionMapper.toTransactionViewDTO(transaction));
     }

    // Helper Methods
    private void checkWalletInDB(Wallet wallet) {
        if (wallet==null) throw new WalletNotFoundException("Wallet not found in DB.");
    }

    public Transaction sendMoney(Double amount, Wallet senderWallet, PublicKey receiverPublicKey) {
        if (senderWallet.getBalance() < amount) {
            throw new WalletInsufficientFundsException("Insufficient Funds");
        }
        Transaction transaction = transactionService.createTransaction(amount, senderWallet.getPublicKey(), receiverPublicKey);
        Signature sign;
        byte[] signature = null;
        try {
            sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(senderWallet.getPrivateKey());
            sign.update(transaction.toString().getBytes(StandardCharsets.UTF_8));
            signature = sign.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new WalletSignatureErrorException(e.getMessage());
        }
        blockChainService.addBlock(transaction, senderWallet.getPublicKey(), signature);
        return transaction;
    }

    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        var keypair = CustomKeyPairGenerator.generateKeyPair();
        wallet.setPrivateKey(keypair.getPrivate());
        wallet.setPublicKey(keypair.getPublic());
        wallet.setBalance(10.0);
        return wallet;
    }
}
