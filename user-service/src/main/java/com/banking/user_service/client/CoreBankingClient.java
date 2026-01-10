package com.banking.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "core-banking-service")
public interface CoreBankingClient {

    @PostMapping("/api/accounts")
    Map<String, Object> createAccount(@RequestBody Map<String, Object> request);
}