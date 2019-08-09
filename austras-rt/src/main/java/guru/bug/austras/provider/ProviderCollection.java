package guru.bug.austras.provider;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProviderCollection<E> implements Collection<Provider<E>> {
    private final List<Provider<E>> providers;

    @SafeVarargs
    public ProviderCollection(Provider<E>... providers) {
        this.providers = List.of(providers);
    }

    public Collection<E> get() {
        return providers.stream().map(Provider::get).collect(Collectors.toList());
    }

    @Override
    public Iterator<Provider<E>> iterator() {
        return providers.iterator();
    }

    @Override
    public Object[] toArray() {

        return providers.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        //noinspection SuspiciousToArrayCall
        return providers.toArray(a);
    }

    @Override
    public int size() {
        return providers.size();
    }

    @Override
    public boolean isEmpty() {
        return providers.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return providers.contains(o);
    }

    @Override
    public boolean add(Provider<E> tProvider) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Provider<E>> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate<? super Provider<E>> filter) {
        throw new UnsupportedOperationException();
    }
}
