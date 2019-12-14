package guru.bug.austras.core;

import guru.bug.austras.meta.QualifierMetaInfo;

import java.util.Set;

public interface Provider<T> {
    T get();

    Set<QualifierMetaInfo> qualifiers();
}
