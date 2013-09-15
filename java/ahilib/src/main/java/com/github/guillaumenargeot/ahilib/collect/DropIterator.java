package com.github.guillaumenargeot.ahilib.collect;

import com.google.common.collect.AbstractIterator;

import java.util.Iterator;

import static com.github.guillaumenargeot.ahilib.base.Preconditions.checkIntArgumentNotNegative;
import static com.github.guillaumenargeot.ahilib.base.Preconditions.checkNotNullArgument;

public final class DropIterator<T> extends AbstractIterator<T> {

    private final int count;
    private final Iterator<T> iterator;
    private boolean initialized = false;

    public DropIterator(final int count, final Iterator<T> iterator) {
        this.count = checkIntArgumentNotNegative(count, "count");
        this.iterator = checkNotNullArgument(iterator, "iterator");
    }

    @Override
    protected final T computeNext() {
        if (!initialized) {
            for (int i = 0; i < count && iterator.hasNext(); i++) {
                iterator.next();
            }
            initialized = true;
        }
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return endOfData();
    }
}
