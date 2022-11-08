package com.retail.rewards.util;

import com.retail.rewards.util.ServiceUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;

public class ServiceUtilTest {

    @InjectMocks
    ServiceUtil serviceUtil;

    @Mock
    ServiceUtil serviceUtilMock;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void isEmailAddressValid_Success () {
        boolean isValid = serviceUtil.isEmailAddressValid("varunnagineni@gmail.com");
        Assert.assertEquals(true, isValid);
    }

    @Test
    public void isEmailAddressValid_Failure () {
        boolean isValid = serviceUtil.isEmailAddressValid("varunnagineni@gmail");
        Assert.assertEquals(false, isValid);
    }

    @Test
    public void getTransactionRewardPointsWhenAbove100_Success () {

        double rewards = serviceUtil.getTransactionRewardPoints(125.5);
        Assert.assertEquals(101.0, rewards, 0);
    }

    @Test
    public void getTransactionRewardPointsWhenAbove50_Success () {

        double rewards = serviceUtil.getTransactionRewardPoints(75.25);
        Assert.assertEquals( 25.25, rewards, 0);
    }

    @Test
    public void getTokensWithCollection_Success() {
        List<String> rewardList = serviceUtil.getTokensWithCollection("reward, program, retail");
        Assert.assertEquals(3, rewardList.size());
    }
}
