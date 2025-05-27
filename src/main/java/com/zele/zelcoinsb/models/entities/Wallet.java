package com.zele.zelcoinsb.models.entities;

import com.zele.zelcoinsb.service.WalletService;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    private Double balance;
}
