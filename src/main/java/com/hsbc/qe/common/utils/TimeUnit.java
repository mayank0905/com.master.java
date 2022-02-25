package com.hsbc.qe.common.utils;

import java.util.Arrays;
import java.util.List;

public enum TimeUnit {

    MILLISECONDS(java.util.concurrent.TimeUnit.MILLISECONDS, "ms", "milli", "millisecond", "milliseconds"),
    SECONDS(java.util.concurrent.TimeUnit.SECONDS, "s", "sec", "second", "seconds"),
    MINUTES(java.util.concurrent.TimeUnit.MINUTES, "m", "min", "minute", "minutes"),
    HOURS(java.util.concurrent.TimeUnit.HOURS, "h", "hour", "hours");

    private final List<String> keys;
    private final java.util.concurrent.TimeUnit timeUnit;

    TimeUnit(java.util.concurrent.TimeUnit timeUnit, String... keys) {
        this.timeUnit = timeUnit;
        this.keys = Arrays.asList(keys);
    }

    public static TimeUnit of(String key) {
        for (TimeUnit timeUnit : values()) {
            if (timeUnit.keys.contains(key.toLowerCase())) return timeUnit;
        }
        throw new IllegalArgumentException("No TimeUnit found for " + key);
    }

    public java.util.concurrent.TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
