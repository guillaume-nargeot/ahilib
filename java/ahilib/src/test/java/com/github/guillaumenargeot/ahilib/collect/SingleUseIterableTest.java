package com.github.guillaumenargeot.ahilib.collect;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

@SuppressWarnings("unchecked")
public final class SingleUseIterableTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenInstantiatedWithNullIterator() {
        new SingleUseIterable<>(null);
    }

    @Test
    public void givenANonNullIteratorWhenCreatingASingleUseIterableThenCallingIteratorShouldReturnANonNullIterator() {
        final Iterator iterator = mock(Iterator.class);
        final Iterable iterable = new SingleUseIterable<>(iterator);

        final Iterator iteratorResult = iterable.iterator();

        assertThat(iteratorResult, is(notNullValue()));
    }

    @Test
    public void givenANonNullIteratorWhenCreatingASingleUseIterableThenCallingIteratorShouldReturnThatIterator() {
        final Iterator iterator = mock(Iterator.class);
        final Iterable iterable = new SingleUseIterable<>(iterator);

        final Iterator iteratorResult = iterable.iterator();

        assertThat(iteratorResult, is(sameInstance(iterator)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenANonNullIteratorWhenCreatingASingleUseIterableThenAnUnsupportedOperationExceptionShouldBeThrownWhenCallingIteratorTwice() {
        final Iterator iterator = mock(Iterator.class);
        final Iterable iterable = new SingleUseIterable<>(iterator);

        iterable.iterator();
        iterable.iterator();
    }
}
