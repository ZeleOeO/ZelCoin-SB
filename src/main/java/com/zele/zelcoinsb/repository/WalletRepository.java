package com.zele.zelcoinsb.repository;

import com.zele.zelcoinsb.models.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.PublicKey;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    public Wallet findByPublicKey(PublicKey publicKey);
}
