package com.github.guillaumenargeot.ahilib.collect;

import com.google.common.collect.AbstractIterator;

import java.util.Iterator;

import static com.github.guillaumenargeot.ahilib.base.Preconditions.checkIntArgumentNotNegative;
import static com.github.guillaumenargeot.ahilib.base.Preconditions.checkNotNullArgument;

public final class TakeIterator<T> extends AbstractIterator<T> {

    private final Iterator<T> iterator;
    private int count;

    public TakeIterator(int count, Iterator<T> iterator) {
        this.count = checkIntArgumentNotNegative(count, "count");
        this.iterator = checkNotNullArgument(iterator, "iterator");
    }

    @Override
    protected final T computeNext() {
        if (count > 0 && iterator.hasNext()) {
            count--;
            return iterator.next();
        }
        return endOfData();
    }
}
