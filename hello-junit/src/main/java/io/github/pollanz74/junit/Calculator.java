package io.github.pollanz74.junit;

import java.math.BigDecimal;

public final class Calculator {

    private Calculator() {
        throw new IllegalStateException("Utility class");
    }

    public static long sum(int a, int b) {
        //return (long) a + b;
        return a + b;
    }

    public static BigDecimal div(BigDecimal a, BigDecimal b) {
        return a.divide(b);
    }

    public static long square(int a) {
        return (long) a * a;
    }

}
