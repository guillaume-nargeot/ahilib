package com.github.guillaumenargeot.ahilib.proxy;

import com.google.common.util.concurrent.Futures;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public final class Synchronous {

    private Synchronous() {
    }

    public static <T> T synchronously(final T asynchronousProxy) {
        checkNotNull(asynchronousProxy);
        checkArgument(Proxy.isProxyClass(asynchronousProxy.getClass()), "asynchronousProxy must be an instance of Proxy");
        checkArgument(hasFutureResultMethods(asynchronousProxy), "asynchronousProxy must provide methods of Future return type");
        final Class proxyClass = asynchronousProxy.getClass();
        final ClassLoader classLoader = proxyClass.getClassLoader();
        final Class[] proxyInterfaces = proxyClass.getInterfaces();
        return unsafeCreateProxy(asynchronousProxy, classLoader, proxyInterfaces);
    }

    private static boolean hasFutureResultMethods(final Object object) {
        for (final Class interfaceClass : object.getClass().getInterfaces()) {
            if (hashFutureResultMethods(interfaceClass)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hashFutureResultMethods(final Class interfaceClass) {
        for (final Method method : interfaceClass.getDeclaredMethods()) {
            if (method.getReturnType().equals(Future.class)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private static <T> T unsafeCreateProxy(final T asynchronousProxy, final ClassLoader classLoader, final Class... proxyInterfaces) {
        return (T) Proxy.newProxyInstance(
                classLoader,
                proxyInterfaces,
                new SynchronousInvocationHandler(asynchronousProxy));
    }

    private static final class SynchronousInvocationHandler implements InvocationHandler {

        private final Object asynchronousProxy;

        private SynchronousInvocationHandler(final Object asynchronousProxy) {
            this.asynchronousProxy = asynchronousProxy;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
            if (!method.getReturnType().equals(Future.class)) {
                throw new UnsupportedOperationException("Cannot transform to synchronous call if the result type is not Future");
            }
            final Future futureResult;
            try {
                futureResult = (Future) method.invoke(asynchronousProxy, args);
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
            final Object result = Futures.getUnchecked(futureResult);
            return Futures.immediateFuture(result);
        }
    }
}
