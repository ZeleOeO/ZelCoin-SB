package com.zele.zelcoinsb.tools;

import com.zele.zelcoinsb.service.WalletService;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomKeyPairGenerator {
    static public KeyPair generateKeyPair() {
        Logger logger = Logger.getLogger(WalletService.class.getName());
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
