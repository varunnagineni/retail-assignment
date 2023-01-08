package com.retail.rewards.util;

import java.math.BigDecimal;

public class Constants {

    public static final String ENROLL_TO_THE_PROGRAM = "ENROLL";
    public static final String REMOVE_TO_THE_PROGRAM = "REMOVE";

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    public static final BigDecimal DEFAULT_VALUE = BigDecimal.valueOf(0.00);

    public static final BigDecimal DOUBLE_POINTS_LIMIT = BigDecimal.valueOf(100.00);

    public static final BigDecimal SINGLE_POINT_LIMIT = BigDecimal.valueOf(50.00);

    public static final String TRANSACTION_APPROVED = "APPROVED";

    public static final String TRANSACTION_DECLINE = "DECLINE";

    public static final String DELIMITER = ",";

}
