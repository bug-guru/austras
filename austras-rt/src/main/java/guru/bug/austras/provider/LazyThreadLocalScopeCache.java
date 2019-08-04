package guru.bug.austras.provider;

import java.util.function.Supplier;

public class LazyThreadLocalScopeCache<T> implements ScopeCache<T> {
    private final ThreadLocal<T> instance = new ThreadLocal<>();

    @Override
    public T get(Supplier<T> factory) {
        var result = instance.get();
        if (result == null) {
            result = factory.get();
            instance.set(result);
        }
        return result;
    }
}
