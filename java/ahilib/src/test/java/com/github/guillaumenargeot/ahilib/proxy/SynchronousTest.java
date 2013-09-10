package com.github.guillaumenargeot.ahilib.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.*;

import static com.github.guillaumenargeot.ahilib.proxy.Synchronous.synchronously;
import static com.google.common.reflect.Reflection.newProxy;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class SynchronousTest {

    @Test
    public void test() {
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        @SuppressWarnings("unchecked")
        final FutureSupplier<String> futureSupplier = newProxy(FutureSupplier.class, new InvocationHandler() {
            @Override
            public Object invoke(final Object target, final Method method, final Object... args) throws Throwable {
                return executorService.schedule(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        return "";
                    }
                }, 100, TimeUnit.MILLISECONDS);
            }
        });

        final Future<String> resultFuture = synchronously(futureSupplier).get();
        // no need to call get() on the future in order to wait for the result

        assertThat(resultFuture.isDone(), is(true));
    }

    private interface FutureSupplier<T> {
        Future<T> get();
    }
}
