package com.retail.rewards.service;

import com.retail.rewards.model.Customer;
import com.retail.rewards.model.CustomerRewardSummary;
import com.retail.rewards.model.RewardTransaction;
import com.retail.rewards.repository.TransactionsRepository;
import com.retail.rewards.util.Constants;
import com.retail.rewards.util.ServiceUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class TransactionRewardServiceTest {

    @InjectMocks
    TransactionRewardService transactionRewardService;

    @Mock
    TransactionsRepository transactionsRepositoryMock;

    @Mock
    ServiceUtil serviceUtilMock;

    @Before
    public void setUp(){
        serviceUtilMock = mock(ServiceUtil.class);
        transactionsRepositoryMock = mock(TransactionsRepository.class);
        transactionRewardService = new TransactionRewardService();
        transactionRewardService.transactionsRepository = transactionsRepositoryMock;
        transactionRewardService.serviceUtil = serviceUtilMock;
    }

    @Test
    public void GivenValidTransactionData_WhenCreateTransactionIsCalled_ThenCreatedRewardTransactionAnd201StatusCodeIsReturned() {

        RewardTransaction rewardTransaction = createTransaction(null, 5L, BigDecimal.valueOf(151.5)
                , Constants.TRANSACTION_APPROVED);
        RewardTransaction rewardTransactionObject = createTransaction(1L, 5L
                , BigDecimal.valueOf(151.5), Constants.TRANSACTION_APPROVED);

        when(transactionsRepositoryMock.save(any(RewardTransaction.class))).thenReturn(rewardTransactionObject);

        when(serviceUtilMock.getTransactionRewardPoints(BigDecimal.valueOf(anyDouble())))
                .thenReturn(BigDecimal.valueOf(anyDouble()));

        RewardTransaction reward = transactionRewardService.createTransaction(rewardTransaction);

        ArgumentCaptor<RewardTransaction> rewardTransactionsArgumentCaptor = ArgumentCaptor.forClass(RewardTransaction.class);
        verify(transactionsRepositoryMock, times(1)).save(rewardTransactionsArgumentCaptor.capture());

        assertEquals(rewardTransactionObject, reward);
    }

    @Test
    public void GivenValidTransactionData_WhenUpdateCustomerRewardTransactionIsCalled_ThenRewardTransactionIsUpdated() {

        Long id = 1L;

        RewardTransaction rewardTransaction = createTransaction(null, 5L, BigDecimal.valueOf(151.5)
                , Constants.TRANSACTION_APPROVED);
        RewardTransaction rewardTransactionObject = createTransaction(id, 5L
                , BigDecimal.valueOf(151.5), Constants.TRANSACTION_DECLINE);

        when(transactionsRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(rewardTransaction));

        Customer customer = Customer.builder().id(5L).build();
        RewardTransaction rewardTrans = RewardTransaction.builder()
                .id(id)
                .customer(customer)
                .transAmount(BigDecimal.valueOf(151.5))
                .transStatus(Constants.TRANSACTION_DECLINE)
                .build();
        when(transactionsRepositoryMock.save(any(RewardTransaction.class))).thenReturn(rewardTransactionObject);
        when(serviceUtilMock.getTransactionRewardPoints(BigDecimal.valueOf(anyDouble())))
                .thenReturn(BigDecimal.valueOf(anyDouble()));

        RewardTransaction reward = transactionRewardService.updateCustomerRewardTransaction(rewardTrans);

        ArgumentCaptor<RewardTransaction> rewardTransactionsArgumentCaptor = ArgumentCaptor.forClass(RewardTransaction.class);
        verify(transactionsRepositoryMock, times(1)).save(rewardTransactionsArgumentCaptor.capture());

        assertEquals(rewardTransactionObject, reward);
        assertEquals(Constants.TRANSACTION_DECLINE, reward.getTransStatus());
    }

    @Test
    public void GivenUnknownCustomerData_WhenUpdateCustomerRewardTransactionIsCalled_ThenNullIsReturned() {

        Long id = 1L;

        when(transactionsRepositoryMock.findById(id)).thenReturn(Optional.empty());

        Customer customer = Customer.builder().id(5L).build();
        RewardTransaction rewardTrans = RewardTransaction.builder()
                .id(id)
                .customer(customer)
                .transAmount(BigDecimal.valueOf(151.5))
                .transStatus(Constants.TRANSACTION_DECLINE)
                .build();
        when(serviceUtilMock.getTransactionRewardPoints(BigDecimal.valueOf(anyDouble())))
                .thenReturn(BigDecimal.valueOf(anyDouble()));

        RewardTransaction reward = transactionRewardService.updateCustomerRewardTransaction(rewardTrans);

        verify(transactionsRepositoryMock, times(1)).findById(id);
        verify(transactionsRepositoryMock, times(0)).save(rewardTrans);

        assertNull(reward);
    }

    @Test
    public void GivenValidCustomerId_WhenGetRewardSummaryByIdIsCalled_ThenCorrespondingCustomerRewardSummaryListAreReturned() {
        Long id = 5L;
        when(transactionsRepositoryMock.getRewardSummaryById(id)).thenReturn(new ArrayList<>());
        List<CustomerRewardSummary> arraySummary = transactionRewardService.getRewardSummary(id);

        verify(transactionsRepositoryMock, times(1)).getRewardSummaryById(id);
    }

    private RewardTransaction createTransaction (Long id, Long custId, BigDecimal transAmount, String transStatus) {

        Customer customer = Customer.builder().id(custId).build();
        return RewardTransaction.builder()
                .id(id)
                .customer(customer)
                .transAmount(transAmount)
                .transStatus(transStatus)
                .build();
    }
}
