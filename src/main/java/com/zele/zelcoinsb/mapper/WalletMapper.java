package com.zele.zelcoinsb.mapper;

import com.zele.zelcoinsb.models.dtos.wallet.WalletViewDTO;
import com.zele.zelcoinsb.models.entities.Wallet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WalletMapper {
    public WalletViewDTO toWalletViewDTO(Wallet wallet) {
        WalletViewDTO walletViewDTO = new WalletViewDTO();
        walletViewDTO.setId(wallet.getId());
        walletViewDTO.setWalletAddress(wallet.getPublicKey().toString());
        walletViewDTO.setAccountBalance(wallet.getBalance());
        return walletViewDTO;
    }
}
