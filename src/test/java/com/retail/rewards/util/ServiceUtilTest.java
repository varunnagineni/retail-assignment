package com.retail.rewards.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ServiceUtilTest {

    @InjectMocks
    ServiceUtil serviceUtil;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void GivenTransactionAmountAbove100_WhenGetTransactionRewardPointsIsCalled_ThenTransactionRewardPointsIsReturned () {

        BigDecimal rewards = serviceUtil.getTransactionRewardPoints(BigDecimal.valueOf(125.5));
        assertEquals(0, rewards.compareTo(BigDecimal.valueOf(101.0)), 0);
    }

    @Test
    public void GivenTransactionAmountAbove50_WhenGetTransactionRewardPointsIsCalled_ThenTransactionRewardPointsIsReturned () {

        BigDecimal rewards = serviceUtil.getTransactionRewardPoints(BigDecimal.valueOf(75.25));
        assertEquals( 0, rewards.compareTo(BigDecimal.valueOf(25.25)), 0);
    }

    @Test
    public void GivenSubscriptionsCommaSeparatedString_WhenGetTokensWithCollectionIsCalled_ThenRewardsListAreReturned() {
        List<String> rewardList = serviceUtil.getTokensWithCollection("reward, program, retail");
        assertEquals(3, rewardList.size());
    }
}
