package guru.bug.austras.provider;

import java.util.function.Supplier;

public class LazySingletonScopeCache<T> implements ScopeCache<T> {
    private T instance;

    @Override
    public T get(Supplier<T> factory) {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = factory.get();
                }
            }
        }
        return instance;
    }
}
