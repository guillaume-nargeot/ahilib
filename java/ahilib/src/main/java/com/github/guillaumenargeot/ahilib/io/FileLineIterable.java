package com.github.guillaumenargeot.ahilib.io;

import com.github.guillaumenargeot.ahilib.collect.SingleUseIterable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

public final class FileLineIterable implements Iterable<String>, AutoCloseable {

    private final AutoCloseable bufferedReader;
    private final Iterable<String> iterable;

    public FileLineIterable(final String filename) throws FileNotFoundException {
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        this.iterable = new SingleUseIterable<>(new BufferedReaderLineIterator(bufferedReader));
        this.bufferedReader = bufferedReader;
    }

    @Override
    public Iterator<String> iterator() {
        return iterable.iterator();
    }

    @Override
    public void close() throws Exception {
        bufferedReader.close();
    }
}