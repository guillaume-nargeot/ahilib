package com.github.guillaumenargeot.ahilib.io;

import com.google.common.collect.AbstractIterator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;

public final class BufferedReaderLineIterator extends AbstractIterator<String> {

    private final BufferedReader bufferedReader;

    public BufferedReaderLineIterator(final BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }


    @Override
    protected String computeNext() {
        final String line;
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            throw new NoSuchElementException(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        if (line != null) {
            return line;
        }
        return endOfData();
    }
}