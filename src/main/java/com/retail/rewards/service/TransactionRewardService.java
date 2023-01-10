package com.retail.rewards.service;

import com.retail.rewards.model.CustomerRewardSummary;
import com.retail.rewards.model.RewardTransaction;
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
     * @param rewardTransaction - Single transaction object will be passed
     * @return - Newly created transaction record will be returned
     */
    public RewardTransaction createTransaction(RewardTransaction rewardTransaction) {

        rewardTransaction.setCreatedDate(new Date());
        rewardTransaction.setRewardsEarned(serviceUtil.getTransactionRewardPoints(rewardTransaction.getTransAmount()));
        return transactionsRepository.save(rewardTransaction);
    }

    /**
     * This method updates transaction status only in any transaction record.
     * Accepted values are APPROVED or DECLINE
     * @param rewardTransaction - Transaction that needs to be updated
     * @return - Updated transaction record will be returned
     */
    public RewardTransaction updateCustomerRewardTransaction(RewardTransaction rewardTransaction) {

        RewardTransaction rewardTrans = transactionsRepository
                .findById(rewardTransaction.getId()).orElse(null);
        if(rewardTrans == null) {
            return null;
        }

        rewardTrans.setTransStatus(rewardTransaction.getTransStatus());
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
