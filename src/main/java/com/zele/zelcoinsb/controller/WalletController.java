package com.zele.zelcoinsb.controller;

import com.zele.zelcoinsb.models.dtos.wallet.WalletViewDTO;
import com.zele.zelcoinsb.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
@AllArgsConstructor
public class WalletController {
    private WalletService walletService;

    @GetMapping("/all")
    public List<WalletViewDTO> getAllWallets() {
        return walletService.getAllWallets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletViewDTO> getWalletById(@PathVariable Long id) {
        return walletService.getWalletById(id);
    }

    @PostMapping("/new")
    public ResponseEntity<WalletViewDTO> createWallet() {
        return walletService.createWalletController();
    }
}
