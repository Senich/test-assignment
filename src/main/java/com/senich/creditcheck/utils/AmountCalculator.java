package com.senich.creditcheck.utils;

import java.math.BigDecimal;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AmountCalculator {

    public static double calcAmount(long qty, double price) {
        return BigDecimal.valueOf(qty).multiply(BigDecimal.valueOf(price)).doubleValue();
    }
}
