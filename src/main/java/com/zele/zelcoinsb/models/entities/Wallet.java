package com.zele.zelcoinsb.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

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

    public Wallet() {
        var keypair = generateKeyPair();
        this.publicKey = keypair.getPublic();
        this.privateKey = keypair.getPrivate();
    }

    private KeyPair generateKeyPair() {
        Logger logger = Logger.getLogger(Wallet.class.getName());
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        assert generator != null;
        generator.initialize(2048);
        return generator.generateKeyPair();
    }
}
