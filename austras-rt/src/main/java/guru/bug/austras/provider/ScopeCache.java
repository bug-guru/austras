package guru.bug.austras.provider;

import java.util.function.Supplier;

public abstract class ScopeCache<T> implements Supplier<T> {
    private final Supplier<T> factory;

    protected ScopeCache(Supplier<T> factory) {
        this.factory = factory;
    }

    protected final T createInstance() {
        return factory.get();
    }
}
