package com.hsbc.qe.common.utils;


public class TimeUtil {

    private final int value;
    private final TimeUnit unit;

    public TimeUtil(int value, TimeUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public static TimeUtil of(int value, String unit) {
        return new TimeUtil(value, TimeUnit.of(unit));
    }

    public int getValue() {
        return value;
    }

    public TimeUnit getUnitDict() {
        return unit;
    }
}
