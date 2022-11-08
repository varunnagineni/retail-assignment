package com.retail.rewards.repository;

import com.retail.rewards.model.CustomerRewardSummary;
import com.retail.rewards.model.RewardTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<RewardTransactions, Long> {

    @Query(value =
            "SELECT r.cust_id as custId, MONTHNAME(r.created_date) as transMonth, sum(r.trans_amount) as rewardsSummary FROM REWARD_TRANSACTIONS r where r.cust_id = ?1 and r.created_date >= DATEADD(M, -3, now()) and r.trans_status = 'APPROVED' group by MONTHNAME(r.created_date)"
            , nativeQuery = true)
    List<CustomerRewardSummary> getRewardSummaryById(Long id);
}
