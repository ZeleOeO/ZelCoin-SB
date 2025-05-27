package com.zele.zelcoinsb.exceptions.wallet;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(String message) {
        super(message);
    }
}
