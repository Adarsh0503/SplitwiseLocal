package com.splitWise.demo.controller;

import com.splitWise.demo.dto.Payment;
import com.splitWise.demo.service.BalanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/balances")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/{groupId}")
    public List<Payment> getGroupBalances(@PathVariable Long groupId) {
        return balanceService.simplifyGroupBalances(groupId);
    }
}
