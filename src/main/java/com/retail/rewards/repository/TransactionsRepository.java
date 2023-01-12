package com.retail.rewards.repository;

import com.retail.rewards.model.CustomerRewardSummary;
import com.retail.rewards.model.RewardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<RewardTransaction, Long> {

    /**
     * Get all the transactions for a particular customer Id for the last 3 months and also transaction status must be approved.
     * @param id
     * @return
     */
    @Query("SELECT r.customer.id as custId, MONTH(r.createdDate) as transMonth, sum(r.transAmount) as rewardsSummary " +
            "FROM RewardTransaction r " +
            "WHERE r.customer.id = ?1 " +
            "AND r.transStatus = 'APPROVED' " +
            "AND r.createdDate >= CURRENT_DATE - 90 " +
            "GROUP BY MONTH(r.createdDate)")
    List<CustomerRewardSummary> getRewardSummaryById(Long id);
}
