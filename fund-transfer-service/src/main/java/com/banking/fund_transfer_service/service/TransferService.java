package com.banking.fund_transfer_service.service;

import com.banking.fund_transfer_service.client.CoreBankingClient;
import com.banking.fund_transfer_service.entity.Transfer;
import com.banking.fund_transfer_service.repository.TransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final CoreBankingClient coreBankingClient;

    public TransferService(TransferRepository transferRepository, CoreBankingClient coreBankingClient) {
        this.transferRepository = transferRepository;
        this.coreBankingClient = coreBankingClient;
    }

    @Transactional
    public Transfer createTransfer(String fromAccountNumber, String toAccountNumber, Double amount, String description) {
        System.out.println("Starting money transfer");
        System.out.println("From: " + fromAccountNumber);
        System.out.println("To: " + toAccountNumber);
        System.out.println("Amount: " + amount);

        //validating input
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        //generating unique reference number
        String referenceNumber = "TXN-" + UUID.randomUUID().toString();

        //creating transfer record with PENDING status
        Transfer transfer = new Transfer(fromAccountNumber, toAccountNumber, amount, description, referenceNumber);
        transfer.setStatus("PENDING");
        Transfer savedTransfer = transferRepository.save(transfer);

        System.out.println("Transfer record created with reference: " + referenceNumber);

        try {
            //verifying sender account exists
            System.out.println("Verifying sender account");
            Map<String, Object> senderAccount = coreBankingClient.getAccount(fromAccountNumber);
            System.out.println("Sender account verified: " + senderAccount.get("accountHolderName"));

            //verifying receiver account exists
            System.out.println("Verifying receiver account");
            Map<String, Object> receiverAccount = coreBankingClient.getAccount(toAccountNumber);
            System.out.println("Receiver account verified: " + receiverAccount.get("accountHolderName"));

            //withdrawing from sender account
            System.out.println("Withdrawing from sender");
            Map<String, Double> withdrawRequest = new HashMap<>();
            withdrawRequest.put("amount", amount);
            Map<String, Object> withdrawResponse = coreBankingClient.withdraw(fromAccountNumber, withdrawRequest);
            System.out.println("Withdraw successful. New balance: " + withdrawResponse.get("balance"));

            //depositing to receiver account
            System.out.println("Depositing to receiver");
            Map<String, Double> depositRequest = new HashMap<>();
            depositRequest.put("amount", amount);
            Map<String, Object> depositResponse = coreBankingClient.deposit(toAccountNumber, depositRequest);
            System.out.println("Deposit successful. New balance: " + depositResponse.get("balance"));

            //updating transfer status to SUCCESS
            savedTransfer.setStatus("SUCCESS");
            transferRepository.save(savedTransfer);

            System.out.println("Transfer Completed Successfully");
            return savedTransfer;

        } catch (Exception e) {
            //if anything fails,transfer is marked as FAILED
            System.err.println("Transfer Failed");
            System.err.println("Error: " + e.getMessage());

            savedTransfer.setStatus("FAILED");
            transferRepository.save(savedTransfer);

            throw new RuntimeException("Transfer failed: " + e.getMessage(), e);
//            return savedTransfer;        //this will save FAILED status in the databse too.
    }
}
public List<Transfer> getAllTransfers() {
    return transferRepository.findAll();
    }

    public Transfer getTransferByReferenceNumber(String referenceNumber) {
        return transferRepository.findByReferenceNumber(referenceNumber)
                .orElseThrow(() -> new RuntimeException("Transfer not found with reference: " + referenceNumber));
    }

    public List<Transfer> getTransfersByAccountNumber(String accountNumber) {
        List<Transfer> sentTransfers = transferRepository.findByFromAccountNumber(accountNumber);
        List<Transfer> receivedTransfers = transferRepository.findByToAccountNumber(accountNumber);

        sentTransfers.addAll(receivedTransfers);
        return sentTransfers;
    }
}
