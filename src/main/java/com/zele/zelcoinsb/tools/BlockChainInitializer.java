package com.zele.zelcoinsb.tools;

import com.zele.zelcoinsb.models.entities.Transaction;
import com.zele.zelcoinsb.models.entities.Wallet;
import com.zele.zelcoinsb.repository.BlockRepository;
import com.zele.zelcoinsb.service.BlockChainService;
import com.zele.zelcoinsb.service.TransactionService;
import com.zele.zelcoinsb.service.WalletService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlockChainInitializer {

    private final WalletService walletService;
    private final BlockChainService blockChainService;
    private final TransactionService transactionService;
    private final BlockRepository blockRepository;

    @PostConstruct
    public void initGenesisBlock() {
        if (blockRepository.count() > 0) return;
        Wallet genesisWallet1 = walletService.createWallet();
        Wallet genesisWallet2 = walletService.createWallet();
        Transaction genesisTransaction = transactionService.createTransaction(0.0, genesisWallet1.getPublicKey(), genesisWallet2.getPublicKey());
        blockChainService.addGenesisBlock(genesisTransaction);
    }
}
