package com.zele.zelcoinsb.exceptions.transaction;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
