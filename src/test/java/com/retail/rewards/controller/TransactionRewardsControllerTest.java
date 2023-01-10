package com.retail.rewards.controller;

import com.retail.rewards.model.Customer;
import com.retail.rewards.model.CustomerRewardSummary;
import com.retail.rewards.model.RewardTransaction;
import com.retail.rewards.service.TransactionRewardService;
import com.retail.rewards.util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class TransactionRewardsControllerTest {

    @InjectMocks
    TransactionRewardsController transactionRewardsController;

    @Mock
    TransactionRewardService rewardServiceMock;

    @Before
    public void setUp() {
        transactionRewardsController = new TransactionRewardsController();
        rewardServiceMock = mock(TransactionRewardService.class);
        transactionRewardsController.transactionRewardService = rewardServiceMock;
    }

    @Test
    public void createTransaction_Success() {
        RewardTransaction rewardTransaction = createTransaction(null, Long.valueOf(5), BigDecimal.valueOf(151.5)
                , Constants.TRANSACTION_APPROVED, BigDecimal.valueOf(0.0));

        RewardTransaction createdTransaction = createTransaction( Long.valueOf(1), Long.valueOf(5), BigDecimal.valueOf(151.5)
                , Constants.TRANSACTION_APPROVED, BigDecimal.valueOf(153));

        when(rewardServiceMock.createTransaction(rewardTransaction)).thenReturn(createdTransaction);

        RewardTransaction transactions = transactionRewardsController.createRewardTransaction(rewardTransaction);
        verify(rewardServiceMock, Mockito.times(1)).createTransaction(rewardTransaction);

        assertEquals(createdTransaction, transactions);
        assertEquals(0, transactions.getRewardsEarned().compareTo(createdTransaction.getRewardsEarned()), 0.0);
    }

    @Test
    public void createTransaction_Exception() {
        IllegalArgumentException exp = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            transactionRewardsController.createRewardTransaction(null);
        });
        assertEquals("Transaction can't be null", exp.getMessage());
    }

    @Test
    public void updateTransaction_Exception() {
        IllegalArgumentException exp = assertThrows(IllegalArgumentException.class, () -> {
            transactionRewardsController.updateRewardTransaction(null);
        });
        assertEquals("Transaction can't be null", exp.getMessage());
    }

    @Test
    public void updateTransaction_Success() {
        RewardTransaction updatedTransaction = createTransaction( Long.valueOf(1), Long.valueOf(5), BigDecimal.valueOf(151.5)
                , Constants.TRANSACTION_DECLINE, BigDecimal.valueOf(153));

        RewardTransaction requestTransaction = createTransaction( Long.valueOf(1), Long.valueOf(5), BigDecimal.valueOf(0.0)
                , Constants.TRANSACTION_DECLINE, BigDecimal.valueOf(0.0));

        when(rewardServiceMock.updateCustomerRewardTransaction(requestTransaction)).thenReturn(updatedTransaction);
        RewardTransaction finalTransaction = transactionRewardsController.updateRewardTransaction(requestTransaction);

        verify(rewardServiceMock, times(1)).updateCustomerRewardTransaction(requestTransaction);
        assertEquals(updatedTransaction, finalTransaction);
        assertEquals(0, finalTransaction.getRewardsEarned().compareTo(updatedTransaction.getRewardsEarned()), 0.0);
        assertEquals(updatedTransaction.getTransStatus(), finalTransaction.getTransStatus());
    }

    @Test
    public void getRewardSummary_Success() {
        Long id = Long.valueOf(1);
        List<CustomerRewardSummary> customerRewardSummaryList = createCustomerRewardSummary();

        when(rewardServiceMock.getRewardSummary(id)).thenReturn(customerRewardSummaryList);

        List<CustomerRewardSummary> custSummaryList = transactionRewardsController.getRewardSummaryByCustId(id);

        verify(rewardServiceMock, times(1)).getRewardSummary(id);

        assertEquals(customerRewardSummaryList.size(), custSummaryList.size());
    }

    private RewardTransaction createTransaction (Long id, Long custId, BigDecimal transAmount
            , String transStatus, BigDecimal rewardsEarned) {

        Customer customer = Customer.builder().id(custId).build();
        return RewardTransaction.builder()
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
