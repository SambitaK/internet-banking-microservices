package com.banking.fund_transfer_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_account_number", nullable = false, length = 50)
    private String fromAccountNumber;

    @Column(name = "to_account_number", nullable = false, length = 50)
    private String toAccountNumber;

    @Column(nullable = false)
    private Double amount;

    @Column(length = 500)
    private String description;

    @Column(name = "reference_number", unique = true, length = 255)
    private String referenceNumber;

    @Column(length = 20)
    private String status;  // SUCCESS, FAILED, PENDING

    @Column(name = "transfer_date")
    private LocalDateTime transferDate;

    // Constructors
    public Transfer() {
        this.transferDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    public Transfer(String fromAccountNumber, String toAccountNumber, Double amount, String description, String referenceNumber) {
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.description = description;
        this.referenceNumber = referenceNumber;
        this.transferDate = LocalDateTime.now();
        this.status = "PENDING";
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromAccountNumber() {
        return fromAccountNumber;
    }

    public void setFromAccountNumber(String fromAccountNumber) {
        this.fromAccountNumber = fromAccountNumber;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }

    public void setToAccountNumber(String toAccountNumber) {
        this.toAccountNumber = toAccountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDateTime transferDate) {
        this.transferDate = transferDate;
    }
}