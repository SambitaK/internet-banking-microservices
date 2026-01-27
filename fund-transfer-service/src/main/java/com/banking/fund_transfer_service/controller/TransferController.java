package com.banking.fund_transfer_service.controller;

import com.banking.fund_transfer_service.entity.Transfer;
import com.banking.fund_transfer_service.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<?> createTransfer(@RequestBody Map<String, Object> request) {
        try {
            String fromAccountNumber = (String) request.get("fromAccountNumber");
            String toAccountNumber = (String) request.get("toAccountNumber");
            Double amount = Double.valueOf(request.get("amount").toString());
            String description = (String) request.get("description");

            Transfer transfer = transferService.createTransfer(fromAccountNumber, toAccountNumber, amount, description);
            return new ResponseEntity<>(transfer, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Transfer failed: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Transfer>> getAllTransfers() {
        List<Transfer> transfers = transferService.getAllTransfers();
        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }

    @GetMapping("/reference/{referenceNumber}")
    public ResponseEntity<?> getTransferByReference(@PathVariable String referenceNumber) {
        try {
            Transfer transfer = transferService.getTransferByReferenceNumber(referenceNumber);
            return new ResponseEntity<>(transfer, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<Transfer>> getTransfersByAccount(@PathVariable String accountNumber) {
        List<Transfer> transfers = transferService.getTransfersByAccountNumber(accountNumber);
        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }
}