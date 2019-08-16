package guru.bug.austras.provider;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionProvider<E> implements Provider<Collection<E>> {
    private final List<Provider<E>> providers;

    @SafeVarargs
    public CollectionProvider(Provider<E>... providers) {
        this.providers = List.of(providers);
    }

    public Collection<E> get() {
        return providers.stream().map(Provider::get).collect(Collectors.toUnmodifiableList());
    }

}
