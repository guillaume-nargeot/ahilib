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

        final FutureTask futureSupplier = newProxy(FutureTask.class, new InvocationHandler() {
            @Override
            public Object invoke(final Object target, final Method method, final Object... args) throws Throwable {
                return executorService.schedule(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        return null;
                    }
                }, 100, TimeUnit.MILLISECONDS);
            }
        });

        final Future<Void> resultFuture = synchronously(futureSupplier).run();
        // no need to block and wait for the result explicitly

        assertThat(resultFuture.isDone(), is(true));
    }

    private interface FutureTask {
        Future<Void> run();
    }
}
