package com.github.guillaumenargeot.ahilib.collect;

import com.google.common.collect.ImmutableList;

import static com.github.guillaumenargeot.ahilib.base.Preconditions.checkIntArgumentNotNegative;
import static com.github.guillaumenargeot.ahilib.base.Preconditions.checkNotNullArgument;

public final class Iterables {

    private Iterables() {
    }

    public static <T> Iterable<T> take(final int count, final Iterable<T> iterable) {
        checkIntArgumentNotNegative(count, "count");
        checkNotNullArgument(iterable, "iterable");
        return new TakeIterable<>(count, iterable);
    }

    public static <T> Iterable<T> drop(final int count, final Iterable<T> iterable) {
        checkIntArgumentNotNegative(count, "count");
        checkNotNullArgument(iterable, "iterable");
        return new DropIterable<>(count, iterable);
    }

    public static <T> Iterable<Iterable<T>> splitAt(int index, Iterable<T> iterable) {
        checkIntArgumentNotNegative(index, "index");
        checkNotNullArgument(iterable, "iterable");
        final Iterable<T> firstHalf = take(index, iterable);
        final Iterable<T> secondHalf = drop(index, iterable);
        return ImmutableList.of(firstHalf, secondHalf);
    }
}