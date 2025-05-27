package com.zele.zelcoinsb.exceptions.wallet;

public class WalletSignatureErrorException extends RuntimeException {
    public WalletSignatureErrorException(String message) {
        super(message);
    }
}
