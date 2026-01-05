package com.banking.core_banking_service.repository;

import com.banking.core_banking_service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountNumber(String accountNumber);

    List<Transaction> findByAccountNumberOrderByTransactionDateDesc(String accountNumber);
}