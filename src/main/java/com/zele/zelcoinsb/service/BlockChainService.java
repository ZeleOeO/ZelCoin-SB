package com.zele.zelcoinsb.service;

import com.zele.zelcoinsb.models.entities.Block;
import com.zele.zelcoinsb.models.entities.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Service
public class BlockChainService {
    private final LedgerService ledgerService;
    private final BlockService blockService;
    private final WalletService walletService;
    private final List<Block> blocks;

    public BlockChainService(LedgerService ledgerService, BlockService blockService,@Lazy WalletService walletService) {
        blocks = new ArrayList<>();
        this.ledgerService = ledgerService;
        this.blockService = blockService;
        this.walletService = walletService;
    }

    public void addGenesisBlock(Transaction transaction) {
        blocks.add(blockService.createBlock("genesis", transaction));
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
            Block newBlock = blockService.createBlock(blocks.getLast().getHash(), transaction);
            blockService.mineBlock(newBlock, 3);
            blocks.add(newBlock);
            walletService.transact(transaction.getSender(), transaction.getReceiver(), transaction.getAmount());
        }
    }

}
