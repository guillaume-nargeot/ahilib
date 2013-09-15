package com.github.guillaumenargeot.ahilib.matcher;

import com.google.common.collect.Iterables;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.github.guillaumenargeot.ahilib.collect.Iterables.splitAt;
import static com.github.guillaumenargeot.ahilib.collect.Iterables.take;
import static com.google.common.collect.Lists.newArrayList;

public final class IterableMatchers {

    private IterableMatchers() {
    }

    public static <T> Matcher<Iterable<T>> isEmpty() {
        return new IterableEqualityMatcher<>(Collections.<T>emptyList());
    }

    public static <T> Matcher<Iterable<T>> isEqualTo(final Iterable<T> iterable) {
        return new IterableEqualityMatcher<>(newArrayList(iterable));
    }

    public static <T> Matcher<Iterable<T>> isEqualTo(final T... elements) {
        return new IterableEqualityMatcher<>(newArrayList(elements));
    }

    public static <T> Matcher<Iterable<T>> startWith(final List<T> iterable) {
        return new IterablePrefixMatcher<>(newArrayList(iterable));
    }

    public static <T> Matcher<Iterable<T>> startWith(final T... elements) {
        return new IterablePrefixMatcher<>(newArrayList(elements));
    }

    private static final class IterableEqualityMatcher<T> extends TypeSafeMatcher<Iterable<T>> {

        private final List<T> expectedValue;

        private IterableEqualityMatcher(final List<T> expectedValue) {
            this.expectedValue = expectedValue;
        }

        @Override
        protected final boolean matchesSafely(Iterable<T> iterable) {
            final int expectedValueLength = expectedValue.size();
            final Iterator<Iterable<T>> splitIterable = splitAt(expectedValueLength, iterable).iterator();
            assert splitIterable.hasNext();
            final Iterable<T> firstHalf = splitIterable.next();
            assert splitIterable.hasNext();
            final Iterable<T> secondHalf = splitIterable.next();
            return newArrayList(firstHalf).equals(expectedValue) && Iterables.isEmpty(secondHalf);
        }

        @Override
        public final void describeTo(Description description) {
            description.appendText("equal to " + Iterables.toString(expectedValue));
        }
    }

    private static final class IterablePrefixMatcher<T> extends TypeSafeMatcher<Iterable<T>> {

        private final List<T> expectedIterablePrefix;

        private IterablePrefixMatcher(final List<T> expectedIterablePrefix) {
            this.expectedIterablePrefix = expectedIterablePrefix;
        }

        @Override
        protected final boolean matchesSafely(final Iterable<T> iterable) {
            final int expectedIterablePrefixLength = expectedIterablePrefix.size();
            final Iterable<T> iterablePrefix = take(expectedIterablePrefixLength, iterable);
            return expectedIterablePrefix.equals(newArrayList(iterablePrefix));
        }

        @Override
        public final void describeTo(final Description description) {
            description.appendText("start with " + Iterables.toString(expectedIterablePrefix));
        }
    }
}
