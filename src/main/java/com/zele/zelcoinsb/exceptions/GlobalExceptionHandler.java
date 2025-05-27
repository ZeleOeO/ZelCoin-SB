package com.zele.zelcoinsb.exceptions;

import com.zele.zelcoinsb.exceptions.transaction.TransactionErrorException;
import com.zele.zelcoinsb.exceptions.transaction.TransactionNotFoundException;
import com.zele.zelcoinsb.exceptions.wallet.WalletInsufficientFundsException;
import com.zele.zelcoinsb.exceptions.wallet.WalletNotFoundException;
import com.zele.zelcoinsb.exceptions.wallet.WalletSignatureErrorException;
import com.zele.zelcoinsb.models.entities.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<String> handleWalletNotFoudException(WalletNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WalletInsufficientFundsException.class)
    public ResponseEntity<String> handleWalletInsufficientFundsException(WalletInsufficientFundsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletSignatureErrorException.class)
    public ResponseEntity<String> handleWalletSignatureErrorException(WalletSignatureErrorException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<String> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionErrorException.class)
    public ResponseEntity<String> handleTransactionErrorException(TransactionErrorException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
