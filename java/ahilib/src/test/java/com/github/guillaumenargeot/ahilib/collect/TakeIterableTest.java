package com.github.guillaumenargeot.ahilib.collect;

import org.junit.Test;

import java.util.Collections;

import static com.github.guillaumenargeot.ahilib.collect.Iterables.take;
import static com.github.guillaumenargeot.ahilib.matcher.IterableMatchers.isEqualTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public final class TakeIterableTest {

    @SuppressWarnings("unchecked")
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionWhenInstantiatedWithNegativeCount() {
        final Iterable<Integer> iterable = mock(Iterable.class);

        new TakeIterable<>(-1, iterable);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnIllegalArgumentExceptionWhenInstantiatedWithNullIterable() {
        new TakeIterable<>(0, null);
    }

    @Test
    public void givenAnEmptySourceIterableWhenTakingZeroElementThenShouldReturnAnEmptyIterable() {
        final Iterable<Integer> emptyIterable = Collections.emptyList();

        final Iterable<Integer> iterable = take(1, emptyIterable);

        assertThat(take(1, iterable), isEqualTo(emptyIterable));
    }
}
