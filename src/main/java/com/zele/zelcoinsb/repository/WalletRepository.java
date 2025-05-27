package com.zele.zelcoinsb.repository;

import com.zele.zelcoinsb.models.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.PublicKey;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    public Optional<Wallet> findByPublicKey(PublicKey publicKey);
}
