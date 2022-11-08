package com.retail.rewards.service;

import com.retail.rewards.model.CustomerRewardSummary;
import com.retail.rewards.model.RewardTransactions;
import com.retail.rewards.repository.TransactionsRepository;
import com.retail.rewards.util.ServiceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionRewardService {

    private static final Logger log = LogManager.getLogger(TransactionRewardService.class);

    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    ServiceUtil serviceUtil;

    public RewardTransactions createTransaction(RewardTransactions rewardTransactions) {

        rewardTransactions.setCreatedDate(new Date());
        rewardTransactions.setRewardsEarned(serviceUtil.getTransactionRewardPoints(rewardTransactions.getTransAmount()));
        return transactionsRepository.save(rewardTransactions);
    }

    public RewardTransactions updateCustomerRewardTransaction(RewardTransactions rewardTransactions) {

        RewardTransactions rewardTrans = transactionsRepository
                .findById(rewardTransactions.getId()).orElse(null);
        if(rewardTrans == null) {
            return null;
        }

        rewardTrans.setTransStatus(rewardTransactions.getTransStatus());
        return transactionsRepository.save(rewardTrans);
    }

    public List<CustomerRewardSummary> getRewardSummary(Long custId) {

        List<CustomerRewardSummary> rewardTransactionsList = transactionsRepository.getRewardSummaryById(custId);
        log.info("Size of the transactionsList : {} ", rewardTransactionsList.size());
        return rewardTransactionsList;
        //return transactionsRepository.getRewardSummaryById(custId);
    }

    public TransactionRewardService(TransactionsRepository transactionsRepository, ServiceUtil serviceUtil) {
        this.transactionsRepository = transactionsRepository;
        this.serviceUtil = serviceUtil;
    }
}
