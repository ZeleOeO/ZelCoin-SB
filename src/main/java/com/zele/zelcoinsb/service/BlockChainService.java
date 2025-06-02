package com.zele.zelcoinsb.service;

import com.zele.zelcoinsb.exceptions.block.BlockNotFoundException;
import com.zele.zelcoinsb.mapper.BlockMapper;
import com.zele.zelcoinsb.models.dtos.block.BlockViewDTO;
import com.zele.zelcoinsb.models.entities.Block;
import com.zele.zelcoinsb.models.entities.Transaction;
import com.zele.zelcoinsb.repository.BlockRepository;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;

    private final List<Block> blocks = new ArrayList<>();
    private final int difficulty = 10;

    public BlockChainService(LedgerService ledgerService, BlockService blockService, BlockRepository blockRepository, BlockMapper blockMapper) {
        this.ledgerService = ledgerService;
        this.blockService = blockService;
        this.blockRepository = blockRepository;
        this.blockMapper = blockMapper;
    }

    public List<BlockViewDTO> getAllBlocks() {
        return blockRepository.findAll()
                .stream()
                .map(blockMapper::toBlockViewDTO)
                .toList();
    }

    public ResponseEntity<BlockViewDTO> getBlocksByHash(String hash) {
        var block = blockRepository.findByHash(hash).orElse(null);
        checkBlockInChain(block, "Incorrect Hash");
        return ResponseEntity.status(HttpStatus.OK).body(blockMapper.toBlockViewDTO(block));
    }

    private void checkBlockInChain(Block block, String message) {
        if (block == null) throw new BlockNotFoundException(message);
    }

    // Helper Methods
    public void addGenesisBlock(Transaction transaction) {
        blocks.add(blockService.createBlock("genesis", transaction));
    }

    public void validateBlock(Transaction transaction, PublicKey publicKey, byte[] signature) {
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
            mineBlock(newBlock, difficulty);
            blocks.add(newBlock);
        }
    }

    // Helper Methods
    public void addGenesisBlock(Transaction transaction) {
        if (blocks.isEmpty()) {
            Block genesis = blockService.createBlock("genesis", List.of(transaction));
            blocks.add(genesis);
            blockRepository.save(genesis);
        }
        System.out.println("Block Mined!!! : " + block.getHash());
    }

    private void checkBlockInChain(Block block, String message) {
        if (block == null) throw new BlockNotFoundException(message);
    }

    public void validateTransaction(Transaction transaction, PublicKey publicKey, byte[] signature) {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initVerify(publicKey);
            sign.update(transaction.toString().getBytes(StandardCharsets.UTF_8));

            boolean isValid = sign.verify(signature);
            if (isValid) {
                transactionRepository.save(transaction);

                Transaction attachedTx = transactionRepository.findById(transaction.getId())
                        .orElseThrow(() -> new TransactionErrorException("Failed to fetch saved transaction"));

                mempool.add(attachedTx);
            }
        } catch (Exception e) {
            throw new TransactionErrorException("Invalid Transaction");
        }
    }
}
