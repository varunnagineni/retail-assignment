package com.retail.rewards.controller;

import com.retail.rewards.model.Customer;
import com.retail.rewards.model.CustomerRewardSummary;
import com.retail.rewards.model.RewardTransaction;
import com.retail.rewards.service.TransactionRewardService;
import com.retail.rewards.util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

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
    public void GivenValidTransactionData_WhenCreateRewardTransactionIsCalled_Then201StatusCodeIsReturned() {
        RewardTransaction rewardTransaction = createTransaction(null, 5L, BigDecimal.valueOf(151.5)
                , Constants.TRANSACTION_APPROVED, BigDecimal.valueOf(0.0));

        RewardTransaction createdTransaction = createTransaction(1L, 5L, BigDecimal.valueOf(151.5)
                , Constants.TRANSACTION_APPROVED, BigDecimal.valueOf(153));

        when(rewardServiceMock.createTransaction(rewardTransaction)).thenReturn(createdTransaction);

        ResponseEntity<RewardTransaction> transaction = transactionRewardsController.createRewardTransaction(rewardTransaction);
        verify(rewardServiceMock, Mockito.times(1)).createTransaction(rewardTransaction);

        assertEquals(HttpStatus.CREATED, transaction.getStatusCode());
        assertEquals(0, transaction.getBody().getRewardsEarned().compareTo(createdTransaction.getRewardsEarned()), 0.0);
    }

    @Test
    public void GivenInappropriateTransactionData_WhenCreateRewardTransactionIsCalled_ThenBadRequestIsReturned() {
        RewardTransaction rewardTransaction = createTransaction(null, 5L, BigDecimal.valueOf(151.5)
                , "", BigDecimal.valueOf(0.0));

        when(rewardServiceMock.createTransaction(rewardTransaction)).thenReturn(null);

        ResponseEntity<RewardTransaction> transaction = transactionRewardsController.createRewardTransaction(rewardTransaction);
        assertEquals(HttpStatus.BAD_REQUEST, transaction.getStatusCode());
        assertNull(transaction.getBody());
    }

    @Test
    public void GivenNUllTransactionData_WhenUpdateRewardTransactionIsCalled_Then404StatusCodeIsReturned() {
        RewardTransaction requestTransaction = createTransaction(120L, 5L, BigDecimal.valueOf(0.0)
                , Constants.TRANSACTION_DECLINE, BigDecimal.valueOf(0.0));
        when(rewardServiceMock.updateCustomerRewardTransaction(requestTransaction)).thenReturn(null);
        ResponseEntity<RewardTransaction> finalTransaction = transactionRewardsController.updateRewardTransaction(requestTransaction);
        verify(rewardServiceMock, times(1)).updateCustomerRewardTransaction(requestTransaction);
        assertEquals(HttpStatus.NOT_FOUND, finalTransaction.getStatusCode());
        assertNull(finalTransaction.getBody());
    }

    @Test
    public void GivenValidTransactionData_WhenUpdateRewardTransactionIsCalled_Then200StatusCodeAndRewardTransactionIsUpdated() {
        RewardTransaction updatedTransaction = createTransaction(1L, 5L, BigDecimal.valueOf(151.5)
                , Constants.TRANSACTION_DECLINE, BigDecimal.valueOf(153));

        RewardTransaction requestTransaction = createTransaction(1L, 5L, BigDecimal.valueOf(0.0)
                , Constants.TRANSACTION_DECLINE, BigDecimal.valueOf(0.0));

        when(rewardServiceMock.updateCustomerRewardTransaction(requestTransaction)).thenReturn(updatedTransaction);
        ResponseEntity<RewardTransaction> finalTransaction = transactionRewardsController.updateRewardTransaction(requestTransaction);

        verify(rewardServiceMock, times(1)).updateCustomerRewardTransaction(requestTransaction);

        assertEquals(HttpStatus.OK, finalTransaction.getStatusCode());
        assertEquals(0, finalTransaction.getBody().getRewardsEarned().compareTo(updatedTransaction.getRewardsEarned()), 0.0);
        assertEquals(updatedTransaction.getTransStatus(), finalTransaction.getBody().getTransStatus());
    }

    @Test
    public void GivenValidCustomerId_WhenGetRewardSummaryByCustIdIsCalled_Then200StatusCodeAndCustomerRewardSummaryListAreReturned() {
        Long id = 1L;
        List<CustomerRewardSummary> customerRewardSummaryList = createCustomerRewardSummary();

        when(rewardServiceMock.getRewardSummary(id)).thenReturn(customerRewardSummaryList);

        ResponseEntity<List<CustomerRewardSummary>> custSummaryList = transactionRewardsController.getRewardSummaryByCustId(id);

        verify(rewardServiceMock, times(1)).getRewardSummary(id);
        assertEquals(HttpStatus.OK, custSummaryList.getStatusCode());
        assertEquals(customerRewardSummaryList.size(), custSummaryList.getBody().size());
    }

    @Test
    public void GivenInValidCustomerId_WhenGetRewardSummaryByCustIdIsCalled_Then404StatusCodeAndCustomerRewardSummaryListAreReturned() {
        Long id = 1L;

        when(rewardServiceMock.getRewardSummary(id)).thenReturn(null);

        ResponseEntity<List<CustomerRewardSummary>> custSummaryList = transactionRewardsController.getRewardSummaryByCustId(id);

        verify(rewardServiceMock, times(1)).getRewardSummary(id);
        assertEquals(HttpStatus.NOT_FOUND, custSummaryList.getStatusCode());
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
