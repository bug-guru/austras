package guru.bug.austras.core;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public abstract class Selector<T> {

    protected abstract List<Provider<T>> getProviders();

    public T selectAny() {
        var list = getProviders();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0).get();
    }

    public T selectSingle() {
        var list = getProviders();
        if (list.isEmpty()) {
            throw new NoSuchElementException();
        } else if (list.size() > 1) {
            throw new IllegalStateException("Too weak selector");
        }
        return list.get(0).get();
    }

    public List<T> selectAll() {
        return getProviders().stream()
                .map(Provider::get)
                .collect(Collectors.toList());
    }

    public Selector<T> withQualifierName(String name) {
        return null;
    }

}
