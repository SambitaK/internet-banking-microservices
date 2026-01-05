package com.banking.core_banking_service.controller;

import com.banking.core_banking_service.entity.Account;
import com.banking.core_banking_service.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Map<String, Object> request) {
        String accountHolderName = (String) request.get("accountHolderName");
        Double initialBalance = Double.valueOf(request.get("initialBalance").toString());
        String accountType = (String) request.get("accountType");

        Account account = accountService.createAccount(accountHolderName, initialBalance, accountType);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber)
                .map(account -> new ResponseEntity<>(account, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable String accountNumber,
                                           @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        Account account = accountService.deposit(accountNumber, amount);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable String accountNumber,
                                            @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        Account account = accountService.withdraw(accountNumber, amount);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
