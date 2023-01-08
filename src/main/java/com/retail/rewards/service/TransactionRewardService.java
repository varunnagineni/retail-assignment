package com.retail.rewards.service;

import com.retail.rewards.model.CustomerRewardSummary;
import com.retail.rewards.model.RewardTransactions;
import com.retail.rewards.repository.TransactionsRepository;
import com.retail.rewards.util.ServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TransactionRewardService {

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    ServiceUtil serviceUtil;

    /**
     * This method accepts only single transaction
     * @param rewardTransactions - Single transaction object will be passed
     * @return - Newly created transaction record will be returned
     */
    public RewardTransactions createTransaction(RewardTransactions rewardTransactions) {

        rewardTransactions.setCreatedDate(new Date());
        rewardTransactions.setRewardsEarned(serviceUtil.getTransactionRewardPoints(rewardTransactions.getTransAmount()));
        return transactionsRepository.save(rewardTransactions);
    }

    /**
     * This method updates transaction status only in any transaction record.
     * Accepted values are APPROVED or DECLINE
     * @param rewardTransactions - Transaction that needs to be updated
     * @return - Updated transaction record will be returned
     */
    public RewardTransactions updateCustomerRewardTransaction(RewardTransactions rewardTransactions) {

        RewardTransactions rewardTrans = transactionsRepository
                .findById(rewardTransactions.getId()).orElse(null);
        if(rewardTrans == null) {
            return null;
        }

        rewardTrans.setTransStatus(rewardTransactions.getTransStatus());
        return transactionsRepository.save(rewardTrans);
    }

    /**
     * Gets the summary for last 3 months, for only transactions that are approved
     * @param custId - Searches based on the custId
     * @return - List of CustomerRewardSummary
     */
    public List<CustomerRewardSummary> getRewardSummary(Long custId) {

        return transactionsRepository.getRewardSummaryById(custId);
    }
}
