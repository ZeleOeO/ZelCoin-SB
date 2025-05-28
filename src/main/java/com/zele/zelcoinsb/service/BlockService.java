package com.zele.zelcoinsb.service;

import com.zele.zelcoinsb.models.entities.Block;
import com.zele.zelcoinsb.models.entities.Transaction;
import com.zele.zelcoinsb.repository.BlockRepository;
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
    private final BlockRepository blockRepository;

    public Block createBlock(String prevHash, Transaction transaction) {
        Block block = new Block();
        block.setHash(calculateHash(block));
        block.setPrevHash(prevHash);
        block.getTransactions().add(transaction);
        block.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        blockRepository.save(block);
        return block;
    }

    public String calculateHash(Block block) {
        Logger logger = Logger.getLogger(Block.class.getName());
        String calculatedHash = block.getPrevHash() + block.getTransactions() + block.getTimestamp() + block.getNonce();
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

}
