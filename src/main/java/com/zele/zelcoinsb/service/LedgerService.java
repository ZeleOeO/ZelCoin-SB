package com.zele.zelcoinsb.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class LedgerService {
    private final Map<PublicKey, Double> balances = new ConcurrentHashMap<PublicKey, Double>() {
    };

    public void credit(PublicKey publicKey, Double amount) {
        double newBalance = getBalance(publicKey) + amount;
        balances.replace(publicKey, newBalance);
    }

    private void debit(PublicKey publicKey, Double amount) {
        double newBalance = getBalance(publicKey) - amount;
        balances.replace(publicKey, newBalance);
    }

    public void addPublicKey(PublicKey publicKey, Double amount) {
        balances.put(publicKey, amount);
    }

    public void transact(PublicKey publicKeySender, PublicKey publicKeyReceiver, Double amount) {
        credit(publicKeyReceiver, amount);
        debit(publicKeySender, amount);
    }

    public double getBalance(PublicKey publicKey) {
        return balances.get(publicKey);
    }

    public Map<PublicKey, Double> getBalances() {
        return balances;
    }
}
