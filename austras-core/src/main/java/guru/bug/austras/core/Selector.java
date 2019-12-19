package guru.bug.austras.core;

import guru.bug.austras.meta.QualifierSetMetaInfo;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Selector<E> implements Provider<Collection<E>> {
    private final List<Provider<? extends E>> providers;

    @SafeVarargs
    public Selector(Provider<? extends E>... providers) {
        this.providers = List.of(providers);
    }

    @Override
    public Collection<E> get() {
        return providers.stream()
                .map(Provider::get)
                .collect(Collectors.toUnmodifiableList());
    }

    public Collection<E> get(Predicate<QualifierSetMetaInfo> filter) {
        return providers.stream()
                .filter(p -> filter.test(p.qualifier()))
                .map(Provider::get)
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<E> any(Predicate<QualifierSetMetaInfo> filter) {
        return providers.stream()
                .filter(p -> filter.test(p.qualifier()))
                .map(Provider::get)
                .findAny()
                .map(v -> (E) v);
    }

    public E single(Predicate<QualifierSetMetaInfo> filter) {
        var tmp = providers.stream()
                .filter(p -> filter.test(p.qualifier()))
                .limit(2)
                .collect(Collectors.toList());
        if (tmp.isEmpty()) {
            throw new NoSuchElementException();
        }
        if (tmp.size() > 2) {
            throw new IllegalStateException();
        }
        return tmp.get(0).get();
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return null;
    }


}
