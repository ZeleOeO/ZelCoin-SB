package com.zele.zelcoinsb.service;

import com.zele.zelcoinsb.models.entities.Block;
import com.zele.zelcoinsb.models.entities.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class BlockService {
    public Block createBlock(String prevHash, Transaction transaction) {
        Block block = new Block();
        block.setPrevHash(prevHash);
        block.setTransaction(transaction);
        block.setHash(calculateHash(block));
        return block;
    }

    public String calculateHash(Block block) {
        Logger logger = Logger.getLogger(Block.class.getName());
        String calculatedHash = block.getPrevHash() + block.getTransaction() + block.getTimestamp() + block.getNonce();
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(calculatedHash.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        StringBuilder builder = new StringBuilder();
        for (byte b : Objects.requireNonNull(hash)) builder.append(String.format("%02x", b));
        return builder.toString();
    }

    public void mineBlock(Block block, int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!block.getHash().substring(0, difficulty).equals(target)) {
            block.setNonce(block.getNonce() + 1);
            block.setHash(calculateHash(block));
        }
        System.out.println("Block Mined!!! : " + block.getHash());
    }
}
