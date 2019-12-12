package guru.bug.austras.core;

import java.util.Collection;

public interface Selector<T> {
    T selectAny();

    T selectSingle();

    Collection<T> selectAll();

    Selector<T> filter(QualifierMatcher qualifierMatcher);


}
