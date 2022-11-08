package com.retail.rewards.service;

import com.retail.rewards.model.Customer;
import com.retail.rewards.model.RewardTransactions;
import com.retail.rewards.repository.TransactionsRepository;
import com.retail.rewards.util.Constants;
import com.retail.rewards.util.ServiceUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class TransactionRewardServiceTest {

    @InjectMocks
    TransactionRewardService transactionRewardService;

    @Mock
    TransactionsRepository transactionsRepositoryMock;

    @Mock
    ServiceUtil serviceUtilMock;

    @Before
    public void setUp(){
        serviceUtilMock = Mockito.mock(ServiceUtil.class);
        transactionsRepositoryMock = Mockito.mock(TransactionsRepository.class);
        transactionRewardService = new TransactionRewardService(transactionsRepositoryMock, serviceUtilMock);
    }

    @Test
    public void createTransaction_Success() {

        RewardTransactions rewardTransaction = createTransaction(null, Long.valueOf(5), 151.5
                , Constants.TRANSACTION_APPROVED);
        RewardTransactions rewardTransactionObject = createTransaction(Long.valueOf(1), Long.valueOf(5)
                , 151.5, Constants.TRANSACTION_APPROVED);

        Mockito.when(transactionsRepositoryMock.save(Mockito.any(RewardTransactions.class))).thenReturn(rewardTransactionObject);

        Mockito.when(serviceUtilMock.getTransactionRewardPoints(Mockito.anyDouble())).thenReturn(Mockito.anyDouble());

        RewardTransactions reward = transactionRewardService.createTransaction(rewardTransaction);

        ArgumentCaptor<RewardTransactions> rewardTransactionsArgumentCaptor = ArgumentCaptor.forClass(RewardTransactions.class);
        Mockito.verify(transactionsRepositoryMock, times(1)).save(rewardTransactionsArgumentCaptor.capture());

        Assert.assertEquals(rewardTransactionObject, reward);
        Assert.assertEquals(rewardTransactionObject.getRewardsEarned(), reward.getRewardsEarned(), 0);
    }

    @Test
    public void updateTransaction_Success() {

        Long id = Long.valueOf(1);

        RewardTransactions rewardTransaction = createTransaction(null, Long.valueOf(5), 151.5
                , Constants.TRANSACTION_APPROVED);
        RewardTransactions rewardTransactionObject = createTransaction(id, Long.valueOf(5)
                , 151.5, Constants.TRANSACTION_DECLINE);

        when(transactionsRepositoryMock.findById(id)).thenReturn(Optional.ofNullable(rewardTransaction));

        Customer customer = Customer.builder().id(Long.valueOf(5)).build();
        RewardTransactions rewardTrans = RewardTransactions.builder()
                .id(id)
                .customer(customer)
                .transAmount(151.5)
                .transStatus(Constants.TRANSACTION_DECLINE)
                .build();
        Mockito.when(transactionsRepositoryMock.save(Mockito.any(RewardTransactions.class))).thenReturn(rewardTransactionObject);
        Mockito.when(serviceUtilMock.getTransactionRewardPoints(Mockito.anyDouble())).thenReturn(Mockito.anyDouble());

        RewardTransactions reward = transactionRewardService.createTransaction(rewardTrans);

        ArgumentCaptor<RewardTransactions> rewardTransactionsArgumentCaptor = ArgumentCaptor.forClass(RewardTransactions.class);
        Mockito.verify(transactionsRepositoryMock, times(1)).save(rewardTransactionsArgumentCaptor.capture());

        Assert.assertEquals(rewardTransactionObject, reward);
        Assert.assertEquals(Constants.TRANSACTION_DECLINE, reward.getTransStatus());
    }

    private RewardTransactions createTransaction (Long id, Long custId, double transAmount, String transStatus) {

        Customer customer = Customer.builder().id(custId).build();
        return RewardTransactions.builder()
                .id(id)
                .customer(customer)
                .transAmount(transAmount)
                .transStatus(transStatus)
                .build();
    }

    private RewardTransactions createTransactionObject (Long id, Long custId, double transAmount, String transStatus) {

        Customer customer = Customer.builder().id(custId).build();
        return RewardTransactions.builder()
                .id(id)
                .customer(customer)
                .transAmount(transAmount)
                .transStatus(transStatus)
                .rewardsEarned(153)
                .build();
    }

}
