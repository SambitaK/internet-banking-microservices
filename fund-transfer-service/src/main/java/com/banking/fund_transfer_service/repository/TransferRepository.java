package com.banking.fund_transfer_service.repository;

import com.banking.fund_transfer_service.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    List<Transfer> findByFromAccountNumber(String fromAccountNumber);

    List<Transfer> findByToAccountNumber(String toAccountNumber);

    Optional<Transfer> findByReferenceNumber(String referenceNumber);

    List<Transfer> findByStatus(String status);
}