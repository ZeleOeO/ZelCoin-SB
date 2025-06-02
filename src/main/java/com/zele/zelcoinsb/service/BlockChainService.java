package com.zele.zelcoinsb.service;

import com.zele.zelcoinsb.exceptions.block.BlockNotFoundException;
import com.zele.zelcoinsb.exceptions.transaction.TransactionErrorException;
import com.zele.zelcoinsb.mapper.BlockMapper;
import com.zele.zelcoinsb.models.dtos.block.BlockViewDTO;
import com.zele.zelcoinsb.models.entities.Block;
import com.zele.zelcoinsb.models.entities.Transaction;
import com.zele.zelcoinsb.repository.BlockRepository;
import com.zele.zelcoinsb.repository.TransactionRepository;
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

    // In-memory blockchain & mempool
    private final List<Block> blocks = new ArrayList<>();
    private final List<Transaction> mempool = new ArrayList<>();

    private final int difficulty = 4;
    private final TransactionRepository transactionRepository;

    public BlockChainService(LedgerService ledgerService, BlockService blockService, BlockRepository blockRepository, BlockMapper blockMapper, TransactionRepository transactionRepository) {
        this.ledgerService = ledgerService;
        this.blockService = blockService;
        this.blockRepository = blockRepository;
        this.blockMapper = blockMapper;
        this.transactionRepository = transactionRepository;
    }

    public List<BlockViewDTO> getAllBlocks() {
        return blocks.stream().map(blockMapper::toBlockViewDTO).toList();
    }

    public ResponseEntity<BlockViewDTO> getBlocksByHash(String hash) {
        var block = blockRepository.findByHash(hash).orElse(null);
        checkBlockInChain(block, "Incorrect Hash");
        return ResponseEntity.status(HttpStatus.OK).body(blockMapper.toBlockViewDTO(block));
    }


    public ResponseEntity<BlockViewDTO> mineBlock() {
        if (mempool.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        Block lastBlock = blocks.isEmpty() ? null : blocks.getLast();
        String previousHash = lastBlock == null ? "genesis" : lastBlock.getHash();

        Block newBlock = blockService.createBlock(previousHash, new ArrayList<>(mempool));

        String target = "0".repeat(difficulty);
        while (!newBlock.getHash().startsWith(target)) {
            newBlock.setNonce(newBlock.getNonce() + 1);
            newBlock.setHash(blockService.calculateHash(newBlock));
        }

        blocks.add(newBlock);
        blockRepository.save(newBlock);
        mempool.clear();

        System.out.println("Block Mined!!! : " + newBlock.getHash());
        return ResponseEntity.status(HttpStatus.OK).body(blockMapper.toBlockViewDTO(newBlock));
    }

    // Helper Methods
    public void addGenesisBlock(Transaction transaction) {
        if (blocks.isEmpty()) {
            Block genesis = blockService.createBlock("genesis", List.of(transaction));
            blocks.add(genesis);
            blockRepository.save(genesis);
        }
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
