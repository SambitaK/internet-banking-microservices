package com.banking.core_banking_service.service;

import com.banking.core_banking_service.entity.Account;
import com.banking.core_banking_service.entity.Transaction;
import com.banking.core_banking_service.repository.AccountRepository;
import com.banking.core_banking_service.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account createAccount(String accountHolderName, Double initialBalance, String accountType) {
        String accountNumber = generateAccountNumber();

        Account account = new Account(accountNumber, accountHolderName, initialBalance, accountType);
        Account savedAccount = accountRepository.save(account);

        if (initialBalance > 0) {
            Transaction transaction = new Transaction(
                    accountNumber,
                    "DEPOSIT",
                    initialBalance,
                    "Initial deposit",
                    UUID.randomUUID().toString()     //unique reference number for this transaction
            );
            transactionRepository.save(transaction);
        }

        return savedAccount;
    }

    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis();
    }

    public Optional<Account> getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    public Account deposit(String accountNumber, Double amount) {
        //validating amount
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        //finding the account
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

        //updating balance
        account.setBalance(account.getBalance() + amount);

        //saving updated account
        Account updatedAccount = accountRepository.save(account);

        //recording transaction
        Transaction transaction = new Transaction(
                accountNumber,
                "DEPOSIT",
                amount,
                "Deposit to account",
                UUID.randomUUID().toString()
        );
        transactionRepository.save(transaction);

        //returning updated account
        return updatedAccount;
    }

    @Transactional
    public Account withdraw(String accountNumber, Double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance. Available: " + account.getBalance());
        }

        account.setBalance(account.getBalance() - amount);

        Account updatedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction(
                accountNumber,
                "WITHDRAWAL",
                amount,
                "Withdrawal from account",
                UUID.randomUUID().toString()
        );
        transactionRepository.save(transaction);

        return updatedAccount;
    }

}
