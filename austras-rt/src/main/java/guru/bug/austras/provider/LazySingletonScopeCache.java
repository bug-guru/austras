package guru.bug.austras.provider;

import java.util.function.Supplier;

public class LazySingletonScopeCache<T> extends ScopeCache<T> {
    private volatile T instance;

    public LazySingletonScopeCache(Supplier<T> factory) {
        super(factory);
    }

    @Override
    public T get() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = createInstance();
                }
            }
        }
        return instance;
    }
}
