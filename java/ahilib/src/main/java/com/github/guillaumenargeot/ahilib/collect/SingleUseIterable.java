package com.github.guillaumenargeot.ahilib.collect;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SingleUseIterable<T> implements Iterable<T> {

    private final AtomicBoolean used = new AtomicBoolean(false);
    private final Iterator<T> iterator;

    public SingleUseIterable(final Iterator<T> iterator) {
        this.iterator = iterator;
    }

    @Override
    public Iterator<T> iterator() {
        if (used.compareAndSet(false, true)) {
            return iterator;
        } else {
            throw new UnsupportedOperationException("");
        }
    }
}
