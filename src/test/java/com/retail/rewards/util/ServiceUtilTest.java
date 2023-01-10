package com.retail.rewards.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

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
        assertTrue(isValid);
    }

    @Test
    public void isEmailAddressValid_Failure () {
        boolean isValid = serviceUtil.isEmailAddressValid("varunnagineni@gmail");
        assertFalse(isValid);
    }

    @Test
    public void getTransactionRewardPointsWhenAbove100_Success () {

        BigDecimal rewards = serviceUtil.getTransactionRewardPoints(BigDecimal.valueOf(125.5));
        assertEquals(0, rewards.compareTo(BigDecimal.valueOf(101.0)), 0);
    }

    @Test
    public void getTransactionRewardPointsWhenAbove50_Success () {

        BigDecimal rewards = serviceUtil.getTransactionRewardPoints(BigDecimal.valueOf(75.25));
        assertEquals( 0, rewards.compareTo(BigDecimal.valueOf(25.25)), 0);
    }

    @Test
    public void getTokensWithCollection_Success() {
        List<String> rewardList = serviceUtil.getTokensWithCollection("reward, program, retail");
        assertEquals(3, rewardList.size());
    }
}
