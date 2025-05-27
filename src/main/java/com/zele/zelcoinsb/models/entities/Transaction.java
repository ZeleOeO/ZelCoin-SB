package com.zele.zelcoinsb.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.security.PublicKey;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private PublicKey sender;
    private PublicKey receiver;

    public Transaction(Double amount, PublicKey senderKey, PublicKey receiverKey) {
        this.amount = amount;
        this.sender = senderKey;
        this.receiver = receiverKey;
    }
}
