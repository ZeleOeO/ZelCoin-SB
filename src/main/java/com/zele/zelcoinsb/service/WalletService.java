package com.zele.zelcoinsb.service;

import com.zele.zelcoinsb.models.entities.Transaction;
import com.zele.zelcoinsb.models.entities.Wallet;
import com.zele.zelcoinsb.tools.CustomKeyPairGenerator;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class WalletService {
    private final BlockChainService blockChainService;
    private final TransactionService transactionService;

    public WalletService(@Lazy BlockChainService blockChainService, TransactionService transactionService) {
        this.blockChainService = blockChainService;
        this.transactionService = transactionService;
    }

    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        var keypair = CustomKeyPairGenerator.generateKeyPair();
        wallet.setPrivateKey(keypair.getPrivate());
        wallet.setPublicKey(keypair.getPublic());
        wallet.setBalance(0.0);
        return wallet;
    }

    public void creditWallet(PublicKey publicKey, Double amount) {
        //TODO implement find wallet by public key to update balance
//        wallet.setBalance(balance);
    }
     public void debitWallet(PublicKey wallet, Double amount) {
         //TODO implement find wallet by public key to update balance
//        wallet.setBalance(balance);
     }

     public void transact(PublicKey sender, PublicKey receiver, Double amount) {
        debitWallet(sender, amount);
        creditWallet(sender, amount);
     }

    public void sendMoney(Double amount, Wallet senderWallet, PublicKey receiverPublicKey) {
        Logger logger = Logger.getLogger(WalletService.class.getName());
        if (senderWallet.getBalance() < amount) {
            logger.log(Level.SEVERE, "Insufficient funds");
            return;
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
            logger.log(Level.SEVERE, e.getMessage());
        }
        blockChainService.addBlock(transaction, senderWallet.getPublicKey(), signature);
    }
}
