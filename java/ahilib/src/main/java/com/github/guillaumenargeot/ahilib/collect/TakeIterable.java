package com.github.guillaumenargeot.ahilib.collect;

import java.util.Iterator;

import static com.github.guillaumenargeot.ahilib.base.Preconditions.checkIntArgumentNotNegative;
import static com.github.guillaumenargeot.ahilib.base.Preconditions.checkNotNullArgument;

public final class TakeIterable<T> implements Iterable<T> {

    private final int count;
    private final Iterable<T> iterable;

    public TakeIterable(final int count, final Iterable<T> iterable) {
        this.count = checkIntArgumentNotNegative(count, "count");
        this.iterable = checkNotNullArgument(iterable, "iterable");
    }

    @Override
    public final Iterator<T> iterator() {
        return new TakeIterator<>(count, iterable.iterator());
    }
}
