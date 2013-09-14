package com.github.guillaumenargeot.ahilib.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class BufferedReaderLineIteratorTest {

    private final BufferedReader bufferedReader = mock(BufferedReader.class);
    private final Iterator<String> iterator = new BufferedReaderLineIterator(bufferedReader);

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenInstantiatedWithNullBufferedReader() {
        new BufferedReaderLineIterator(null);
    }

    @Test
    public void givenAnEmptyBufferedReaderWhenIteratingThenHasNextShouldReturnFalse() throws IOException {
        when(bufferedReader.readLine()).thenReturn(null);

        final boolean hasNextResult = iterator.hasNext();

        assertThat(hasNextResult, is(false));
    }

    @Test
    public void givenBufferedReaderWithOneNonNullLineWhenIteratingThenHasNextShouldReturnTrue() throws IOException {
        when(bufferedReader.readLine()).thenReturn("one non-null line");

        final boolean hasNextResult = iterator.hasNext();

        assertThat(hasNextResult, is(true));
    }

    @Test
    public void givenBufferedReaderWithOneNonNullLineWhenIteratingThenNextShouldReturnANonNullString() throws IOException {
        when(bufferedReader.readLine()).thenReturn("one non-null line");

        iterator.hasNext();
        final String nextResult = iterator.next();

        assertThat(nextResult, is(notNullValue()));
    }

    @Test
    public void givenBufferedReaderWithOneNonNullLineWhenIteratingThenNextShouldReturnAStringEqualToThatLine() throws IOException {
        final String line = "one non-null line";
        when(bufferedReader.readLine()).thenReturn(line);

        iterator.hasNext();
        final String nextResult = iterator.next();

        assertThat(nextResult, is(sameInstance(line)));
    }

    @Test
    public void givenBufferedReaderWithOnlyOneNonNullLineWhenIteratingThenSecondCallToHasNextShouldReturnFalse() throws IOException {
        when(bufferedReader.readLine()).thenReturn("one non-null line").thenReturn(null);

        iterator.hasNext();
        iterator.next();
        final boolean hasNextResult = iterator.hasNext();

        assertThat(hasNextResult, is(false));
    }
}
