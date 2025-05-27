package com.zele.zelcoinsb.mapper;

import com.zele.zelcoinsb.models.dtos.transaction.TransactionViewDTO;
import com.zele.zelcoinsb.models.entities.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionMapper {
    public TransactionViewDTO toTransactionViewDTO(Transaction transaction) {
        TransactionViewDTO transactionViewDTO = new TransactionViewDTO();
        transactionViewDTO.setTx_id(transaction.getId());
        transactionViewDTO.setAmount(transaction.getAmount());
        transactionViewDTO.setSenderWallet(transaction.getSender().toString());
        transactionViewDTO.setReceiverWallet(transaction.getReceiver().toString());
        transactionViewDTO.setCreatedAt(transaction.getTimestamp());
        return transactionViewDTO;
    }
}
