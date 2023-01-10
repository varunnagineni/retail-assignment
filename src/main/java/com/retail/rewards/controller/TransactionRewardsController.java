package com.retail.rewards.controller;

import com.retail.rewards.model.CustomerRewardSummary;
import com.retail.rewards.model.RewardTransaction;
import com.retail.rewards.service.TransactionRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionRewardsController {

    @Autowired
    TransactionRewardService transactionRewardService;

    @PostMapping("/transaction")
    public RewardTransaction createRewardTransaction(@RequestBody RewardTransaction rewardTransaction) {
        if (rewardTransaction == null) {
            throw new IllegalArgumentException("Transaction can't be null");
        }
        return transactionRewardService.createTransaction(rewardTransaction);
    }

    @PutMapping("/transaction")
    public RewardTransaction updateRewardTransaction(@RequestBody RewardTransaction rewardTransaction) {
        if (rewardTransaction == null) {
            throw new IllegalArgumentException("Transaction can't be null");
        }
        return transactionRewardService.updateCustomerRewardTransaction(rewardTransaction);
    }

    @GetMapping("/summary/{id}")
    public List<CustomerRewardSummary> getRewardSummaryByCustId(@PathVariable Long id) {
        return transactionRewardService.getRewardSummary(id);
    }
}
