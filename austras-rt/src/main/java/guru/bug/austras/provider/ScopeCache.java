package guru.bug.austras.provider;

import java.util.function.Supplier;

public interface ScopeCache<T> {
    T get(Supplier<T> factory);
}
