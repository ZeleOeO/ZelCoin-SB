package com.zele.zelcoinsb.service;

import com.zele.zelcoinsb.models.entities.Block;
import com.zele.zelcoinsb.models.entities.Transaction;
import com.zele.zelcoinsb.models.entities.Wallet;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Service
@AllArgsConstructor
public class BlockChainService {
    private final LedgerService ledgerService;
    private final WalletService walletService;
    private List<Block> blocks;
    private LedgerService ledger;

    @PostConstruct
    public void addGenesisBlock() {
        Wallet genesisWallet1 = walletService.createWallet();
        Wallet genesisWallet2 = walletService.createWallet();
        Transaction genesisTransaction1 = new Transaction(0.0, genesisWallet1.getPublicKey(), genesisWallet2.getPublicKey());
        blocks.add(new Block("0", genesisTransaction1));
    }

    public void addBlock(Transaction transaction, PublicKey publicKey, byte[] signature) {
        Logger logger = Logger.getLogger(BlockChainService.class.getName());
        Signature sign;
        boolean isValid = false;
        try {
            sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(publicKey);
            sign.update(transaction.toString().getBytes(StandardCharsets.UTF_8));
            isValid = sign.verify(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        if (isValid) {
            Block newBlock = new Block(blocks.getLast().getHash(), transaction);
            newBlock.mineBlock(3);
            blocks.add(newBlock);
            ledgerService.transact(transaction.getSender(), transaction.getReceiver(), transaction.getAmount());
        }
    }

}
