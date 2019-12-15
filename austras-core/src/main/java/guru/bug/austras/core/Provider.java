package guru.bug.austras.core;

import guru.bug.austras.meta.QualifierSetMetaInfo;

public interface Provider<T> {
    T get();

    QualifierSetMetaInfo meta();
}
