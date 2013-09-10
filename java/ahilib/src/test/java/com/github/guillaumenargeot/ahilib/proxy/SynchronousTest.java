package com.github.guillaumenargeot.ahilib.proxy;

import com.google.common.util.concurrent.Futures;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.Future;

import static com.github.guillaumenargeot.ahilib.proxy.Synchronous.synchronously;
import static com.google.common.reflect.Reflection.newProxy;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class SynchronousTest {

    @Test
    public void test() {
        final String futureResult = "result";

        @SuppressWarnings("unchecked")
        final FutureSupplier<String> futureSupplier = newProxy(FutureSupplier.class, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                return Futures.immediateFuture(futureResult);
            }
        });

        final Future<String> resultFuture = synchronously(futureSupplier).get();
        final String result = Futures.getUnchecked(resultFuture);

        assertThat(result, is(equalTo(futureResult)));
    }

    private interface FutureSupplier<T> {
        Future<T> get();
    }
}
