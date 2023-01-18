package com.retail.rewards.controller;

import com.retail.rewards.model.CustomerRewardSummary;
import com.retail.rewards.model.RewardTransaction;
import com.retail.rewards.service.TransactionRewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionRewardsController {

    @Autowired
    TransactionRewardService transactionRewardService;

    @PostMapping("/transaction")
    public ResponseEntity<RewardTransaction> createRewardTransaction(@RequestBody RewardTransaction rewardTransaction) {

        RewardTransaction createdRewardTransaction = transactionRewardService.createTransaction(rewardTransaction);

        if (createdRewardTransaction == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdRewardTransaction, HttpStatus.CREATED);
    }

    @PutMapping("/transaction")
    public ResponseEntity<RewardTransaction> updateRewardTransaction(@RequestBody RewardTransaction rewardTransaction) {
        RewardTransaction updatedRewardTransaction = transactionRewardService.updateCustomerRewardTransaction(rewardTransaction);
        if (updatedRewardTransaction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedRewardTransaction, HttpStatus.OK);
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<List<CustomerRewardSummary>> getRewardSummaryByCustId(@PathVariable Long id) {

        List<CustomerRewardSummary> customerRewardSummaryList = transactionRewardService.getRewardSummary(id);
        if (customerRewardSummaryList == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(customerRewardSummaryList, HttpStatus.OK);
    }
}
