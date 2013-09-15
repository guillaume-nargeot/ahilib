package com.github.guillaumenargeot.ahilib.base;

import static com.google.common.base.Preconditions.checkArgument;

public final class Preconditions {
    private static final String NULL_ARGUMENT_MSG = "%s is null";
    private static final String NEGATIVE_ARGUMENT_MSG = "%s is negative";

    private Preconditions() {
    }

    public static <T> T checkNotNullArgument(final T argument, final String name) {
        checkArgument(argument != null, NULL_ARGUMENT_MSG, name);
        return argument;
    }

    public static int checkIntArgumentNotNegative(final int value, final String name) {
        checkArgument(value >= 0, NEGATIVE_ARGUMENT_MSG, name);
        return value;
    }
}
