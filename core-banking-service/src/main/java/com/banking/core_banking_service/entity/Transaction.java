package com.banking.core_banking_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String accountNumber;

    @Column(nullable = false, length = 20)
    private String transactionType;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(length = 500)
    private String description;

    private String referenceNumber;

    public Transaction() {
        this.transactionDate = LocalDateTime.now();
    }

    public Transaction(String accountNumber, String transactionType, Double amount, String description, String referenceNumber) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.referenceNumber = referenceNumber;
        this.transactionDate = LocalDateTime.now();
    }
}
