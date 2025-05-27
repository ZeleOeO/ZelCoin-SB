package com.zele.zelcoinsb.exceptions.wallet;

public class WalletInsufficientFundsException extends RuntimeException {
    public WalletInsufficientFundsException(String message) {
        super(message);
    }
}
