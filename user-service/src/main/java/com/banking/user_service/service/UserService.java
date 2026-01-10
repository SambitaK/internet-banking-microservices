package com.banking.user_service.service;

import com.banking.user_service.client.CoreBankingClient;
import com.banking.user_service.entity.User;
import com.banking.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CoreBankingClient coreBankingClient;

    public UserService(UserRepository userRepository, CoreBankingClient coreBankingClient) {
        this.userRepository = userRepository;
        this.coreBankingClient = coreBankingClient;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User registerUser(String username, String email, String phone, Double initialBalance) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        String accountNumber = null;

        try {
            Map<String, Object> accountRequest = new HashMap<>();
            accountRequest.put("accountHolderName", username);
            accountRequest.put("initialBalance", initialBalance);
            accountRequest.put("accountType", "SAVINGS");

            System.out.println("=== Calling Core Banking Service ===");
            System.out.println("Request: " + accountRequest);

            Map<String, Object> accountResponse = coreBankingClient.createAccount(accountRequest);

            System.out.println("=== Response from Core Banking ===");
            System.out.println("Full Response: " + accountResponse);

            accountNumber = (String) accountResponse.get("accountNumber");
            System.out.println("Extracted Account Number: " + accountNumber);

        } catch (Exception e) {
            System.err.println("=== FEIGN CALL FAILED ===");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to create bank account: " + e.getMessage(), e);
        }

        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new RuntimeException("Account number is null - Core Banking Service did not return account number");
        }

        User user = new User(username, email, phone, accountNumber);
        User savedUser = userRepository.save(user);

        System.out.println("=== User Saved Successfully ===");
        System.out.println("User ID: " + savedUser.getId());
        System.out.println("Account Number: " + savedUser.getAccountNumber());

        return savedUser;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}