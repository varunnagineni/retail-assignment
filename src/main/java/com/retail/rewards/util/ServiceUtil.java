package com.retail.rewards.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ServiceUtil {

    private static final Logger LOG = LogManager.getLogger(ServiceUtil.class);

    public boolean isEmailAddressValid(String emailId) {
        return Pattern.compile(Constants.EMAIL_REGEX).matcher(emailId).matches();
    }

    public List<String> getTokensWithCollection(String str) {
        return Collections.list(new StringTokenizer(str, ",")).stream()
                .map(token -> (String) token)
                .collect(Collectors.toList());
    }

    public double getTransactionRewardPoints(double transAmount) {

        if (transAmount > 100) {
            return ((transAmount - 100) * 2) + 50;
        }

        if (transAmount > 50) {
            return ((transAmount - 50) * 1);
        }

        return Constants.DEFAULT_VALUE;
    }
}
