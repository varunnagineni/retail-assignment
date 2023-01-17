package com.retail.rewards.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ServiceUtil {

    /**
     * It gets a Delimiter seperated string (Here its ',') and returns lists of strings
     * @param str
     * @return
     */
    public List<String> getTokensWithCollection(String str) {
        return Collections.list(new StringTokenizer(str, Constants.DELIMITER)).stream()
                .map(token -> (String) token)
                .collect(Collectors.toList());
    }

    /**
     * Calculates the rewards for the given transaction amount.
     *      If the amount is greater than 100,
     *          customer receives 2 points for every dollar spent over $100 and 1 point for every dollar spent over $50
     *          (Ex: Amount spent => $150 = 2*$50 + 1*$50 = 150 points)
     *
     *      If the amount is greater than 50 and less than 100,
     *          customer receives 1 point for every dollar spent over $50.
     * @param transAmount
     * @return
     */
    public BigDecimal getTransactionRewardPoints(BigDecimal transAmount) {
        log.debug("Tansaction Amount provided : " + transAmount);
        return transAmount.compareTo(Constants.DOUBLE_POINTS_LIMIT) > 0
                ? transAmount.subtract(Constants.DOUBLE_POINTS_LIMIT).multiply(BigDecimal.valueOf(2)).add(Constants.SINGLE_POINT_LIMIT)
                : transAmount.compareTo(Constants.SINGLE_POINT_LIMIT) > 0
                ? transAmount.subtract(Constants.SINGLE_POINT_LIMIT).multiply(BigDecimal.valueOf(1))
                : Constants.DEFAULT_VALUE;
    }
}
