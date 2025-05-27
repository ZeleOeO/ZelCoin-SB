package com.zele.zelcoinsb.exceptions;

import com.zele.zelcoinsb.exceptions.wallet.WalletInsufficientFundsException;
import com.zele.zelcoinsb.exceptions.wallet.WalletNotFoundException;
import com.zele.zelcoinsb.exceptions.wallet.WalletSignatureErrorException;
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
}
