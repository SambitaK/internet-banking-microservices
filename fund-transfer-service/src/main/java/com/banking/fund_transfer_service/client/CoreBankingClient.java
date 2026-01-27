package com.banking.fund_transfer_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "core-banking-service")
public interface CoreBankingClient {
    
    @GetMapping("/api/accounts/{accountNumber}")
    Map<String, Object> getAccount(@PathVariable("accountNumber") String accountNumber);
    
    @PostMapping("/api/accounts/{accountNumber}/withdraw")
    Map<String, Object> withdraw(@PathVariable("accountNumber") String accountNumber, 
                                  @RequestBody Map<String, Double> request);
    
    @PostMapping("/api/accounts/{accountNumber}/deposit")
    Map<String, Object> deposit(@PathVariable("accountNumber") String accountNumber,
                                 @RequestBody Map<String, Double> request);
}