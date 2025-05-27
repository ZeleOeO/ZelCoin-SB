package com.zele.zelcoinsb.service;

import com.zele.zelcoinsb.models.entities.Wallet;
import com.zele.zelcoinsb.models.entities.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class WalletService {
    private final LedgerService ledgerService;
    private final @Lazy BlockChainService blockChainService;

    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        var keypair = generateKeyPair();
        wallet.setPrivateKey(keypair.getPrivate());
        wallet.setPublicKey(keypair.getPublic());
        wallet.setBalance(0.0);
        return wallet;
    }


    public void sendMoney(Double amount, Wallet senderWallet, PublicKey receiverPublicKey) {
        Logger logger = Logger.getLogger(WalletService.class.getName());
        if (ledgerService.getBalance(senderWallet.getPublicKey()) < amount) {
            logger.log(Level.SEVERE, "Insufficient funds");
            return;
        }
        var transaction = new Transaction(amount, senderWallet.getPublicKey(), receiverPublicKey);
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

    private KeyPair generateKeyPair() {
        Logger logger = Logger.getLogger(WalletService.class.getName());
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        assert generator != null;
        generator.initialize(2048);
        return generator.generateKeyPair();
    }
}
