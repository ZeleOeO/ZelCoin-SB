package com.zele.zelcoinsb.exceptions.transaction;

public class TransactionErrorException extends RuntimeException {
    public TransactionErrorException(String message) {
        super(message);
    }
}
