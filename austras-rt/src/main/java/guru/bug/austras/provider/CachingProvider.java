package guru.bug.austras.provider;

public abstract class CachingProvider<T> implements Provider<T> {
    private final ScopeCache<T> scopeCache;

    protected CachingProvider(ScopeCache<T> scopeCache) {
        this.scopeCache = scopeCache;
    }

    protected abstract T createInstance();

    @Override
    public final T get() {
        return scopeCache.get(this::createInstance);
    }
}
