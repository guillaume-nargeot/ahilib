package com.github.guillaumenargeot.ahilib.proxy;

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
        final Class<?>[] proxyInterfaces = asynchronousProxy.getClass().getInterfaces();
        //noinspection unchecked
        return (T) Proxy.newProxyInstance(
                asynchronousProxy.getClass().getClassLoader(),
                proxyInterfaces,
                new SynchronousInvocationHandler<T>(asynchronousProxy)
        );
    }

    private static final class SynchronousInvocationHandler<T> implements InvocationHandler {

        private final T asynchronousProxy;

        private SynchronousInvocationHandler(final T asynchronousProxy) {
            this.asynchronousProxy = asynchronousProxy;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
            if (!method.getReturnType().equals(Future.class)) {
                throw new UnsupportedOperationException("");
            }
            try {
                return method.invoke(asynchronousProxy, objects);
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
    }
}
