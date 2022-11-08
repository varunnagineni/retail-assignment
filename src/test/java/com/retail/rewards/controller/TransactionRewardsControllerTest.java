package com.retail.rewards.controller;

import com.retail.rewards.model.Customer;
import com.retail.rewards.model.CustomerRewardSummary;
import com.retail.rewards.model.RewardTransactions;
import com.retail.rewards.service.TransactionRewardService;
import com.retail.rewards.util.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class TransactionRewardsControllerTest {

    @InjectMocks
    TransactionRewardsController transactionRewardsController;

    @Mock
    TransactionRewardService rewardServiceMock;

    @Before
    public void setUp() {
        transactionRewardsController = new TransactionRewardsController();
        rewardServiceMock = Mockito.mock(TransactionRewardService.class);
        transactionRewardsController.transactionRewardService = rewardServiceMock;
    }

    @Test
    public void createTransaction_Success() {
        RewardTransactions rewardTransaction = createTransaction(null, Long.valueOf(5), 151.5
                , Constants.TRANSACTION_APPROVED, 0.0);

        RewardTransactions createdTransaction = createTransaction( Long.valueOf(1), Long.valueOf(5), 151.5
                , Constants.TRANSACTION_APPROVED, 153);

        Mockito.when(rewardServiceMock.createTransaction(rewardTransaction)).thenReturn(createdTransaction);

        RewardTransactions transactions = transactionRewardsController.createRewardTransaction(rewardTransaction);
        Mockito.verify(rewardServiceMock, Mockito.times(1)).createTransaction(rewardTransaction);

        Assert.assertEquals(createdTransaction, transactions);
        Assert.assertEquals(createdTransaction.getRewardsEarned(), transactions.getRewardsEarned(), 0.0);
    }

    @Test
    public void createTransaction_Exception() {
        IllegalArgumentException exp = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            transactionRewardsController.createRewardTransaction(null);
        });
        Assert.assertEquals("Transaction can't be null", exp.getMessage());
    }

    @Test
    public void updateTransaction_Exception() {
        IllegalArgumentException exp = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            transactionRewardsController.updateRewardTransaction(null);
        });
        Assert.assertEquals("Transaction can't be null", exp.getMessage());
    }

    @Test
    public void updateTransaction_Success() {
        RewardTransactions updatedTransaction = createTransaction( Long.valueOf(1), Long.valueOf(5), 151.5
                , Constants.TRANSACTION_DECLINE, 153);

        RewardTransactions requestTransaction = createTransaction( Long.valueOf(1), Long.valueOf(5), 0.0
                , Constants.TRANSACTION_DECLINE,  0.0);

        Mockito.when(rewardServiceMock.updateCustomerRewardTransaction(requestTransaction)).thenReturn(updatedTransaction);
        RewardTransactions finalTransaction = transactionRewardsController.updateRewardTransaction(requestTransaction);

        Mockito.verify(rewardServiceMock, Mockito.times(1)).updateCustomerRewardTransaction(requestTransaction);
        Assert.assertEquals(updatedTransaction, finalTransaction);
        Assert.assertEquals(updatedTransaction.getRewardsEarned(), finalTransaction.getRewardsEarned(), 0.0);
        Assert.assertEquals(updatedTransaction.getTransStatus(), finalTransaction.getTransStatus());
    }

    @Test
    public void getRewardSummary_Success() {
        Long id = Long.valueOf(1);
        List<CustomerRewardSummary> customerRewardSummaryList = createCustomerRewardSummary();

        Mockito.when(rewardServiceMock.getRewardSummary(id)).thenReturn(customerRewardSummaryList);

        List<CustomerRewardSummary> custSummaryList = transactionRewardsController.getRewardSummaryByCustId(id);

        Mockito.verify(rewardServiceMock, Mockito.times(1)).getRewardSummary(id);

        Assert.assertEquals(customerRewardSummaryList.size(), custSummaryList.size());
    }

    private RewardTransactions createTransaction (Long id, Long custId, double transAmount
            , String transStatus, double rewardsEarned) {

        Customer customer = Customer.builder().id(custId).build();
        return RewardTransactions.builder()
                .id(id)
                .customer(customer)
                .transAmount(transAmount)
                .transStatus(transStatus)
                .rewardsEarned(rewardsEarned)
                .build();
    }

    private List<CustomerRewardSummary> createCustomerRewardSummary() {

        List<CustomerRewardSummary> customerRewardSummaryList = new ArrayList<>();
        CustomerRewardSummary customerRewardSummary = new CustomerRewardSummary() {
            @Override
            public String getCustId() {
                return "1";
            }

            @Override
            public String getTransMonth() {
                return "November";
            }

            @Override
            public double getRewardsSummary() {
                return 75.5;
            }
        };
        customerRewardSummaryList.add(customerRewardSummary);

        customerRewardSummary = new CustomerRewardSummary() {
            @Override
            public String getCustId() {
                return "1";
            }

            @Override
            public String getTransMonth() {
                return "October";
            }

            @Override
            public double getRewardsSummary() {
                return 15.5;
            }
        };
        customerRewardSummaryList.add(customerRewardSummary);

        customerRewardSummary = new CustomerRewardSummary() {
            @Override
            public String getCustId() {
                return "1";
            }

            @Override
            public String getTransMonth() {
                return "September";
            }

            @Override
            public double getRewardsSummary() {
                return 85.5;
            }
        };
        customerRewardSummaryList.add(customerRewardSummary);

        return customerRewardSummaryList;
    }
}
;
