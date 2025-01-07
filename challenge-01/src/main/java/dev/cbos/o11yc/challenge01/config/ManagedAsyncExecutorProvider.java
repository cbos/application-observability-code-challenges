package dev.cbos.o11yc.challenge01.config;

import org.glassfish.jersey.server.ManagedAsyncExecutor;
import org.glassfish.jersey.spi.ThreadPoolExecutorProvider;

@ManagedAsyncExecutor
public class ManagedAsyncExecutorProvider extends ThreadPoolExecutorProvider {
    public ManagedAsyncExecutorProvider() {
        super("challenge01-managed-async-executor");
    }

    @Override
    protected int getCorePoolSize() {
        return 4;
    }

    @Override
    protected int getMaximumPoolSize() {
        return 40;
    }
}
