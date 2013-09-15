package com.github.guillaumenargeot.ahilib.collect;

import java.util.Iterator;

import static com.github.guillaumenargeot.ahilib.base.Preconditions.checkIntArgumentNotNegative;
import static com.github.guillaumenargeot.ahilib.base.Preconditions.checkNotNullArgument;

public final class Iterators {

    private Iterators() {
    }

    public static <T> Iterator<T> take(final int count, final Iterator<T> iterator) {
        checkIntArgumentNotNegative(count, "count");
        checkNotNullArgument(iterator, "iterator");
        return new TakeIterator<>(count, iterator);
    }

    public static <T> Iterator<T> drop(final int count, final Iterator<T> iterator) {
        checkIntArgumentNotNegative(count, "count");
        checkNotNullArgument(iterator, "iterator");
        return new DropIterator<>(count, iterator);
    }
}
