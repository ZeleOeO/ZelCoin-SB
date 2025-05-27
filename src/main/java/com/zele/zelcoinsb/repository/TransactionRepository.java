package com.zele.zelcoinsb.repository;

import com.zele.zelcoinsb.models.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
