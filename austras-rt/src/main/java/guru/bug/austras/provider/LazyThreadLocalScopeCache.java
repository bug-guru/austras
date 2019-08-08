package guru.bug.austras.provider;

import java.util.function.Supplier;

public class LazyThreadLocalScopeCache<T> extends ScopeCache<T> {
    private final ThreadLocal<T> instance;

    public LazyThreadLocalScopeCache(Supplier<T> factory) {
        super(factory);
        instance = ThreadLocal.withInitial(this::createInstance);
    }

    @Override
    public T get() {
        return instance.get();
    }
}
