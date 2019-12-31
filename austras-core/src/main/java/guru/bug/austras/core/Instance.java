/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.core;

import guru.bug.austras.core.qualifiers.QualifierSetInfo;

import java.util.Objects;
import java.util.function.Consumer;

public class Instance<T> {
    private final T component;
    private final QualifierSetInfo qualifiers;

    private Instance(T component, QualifierSetInfo qualifiers) {
        this.component = Objects.requireNonNull(component, "component");
        this.qualifiers = Objects.requireNonNull(qualifiers, "qualifiers");
    }

    public static <T> Instance<T> of(T component) {
        return new Instance<>(component, QualifierSetInfo.defaultQualifier());
    }

    public static <T> Instance<T> of(T component, QualifierSetInfo qualifiers) {
        return new Instance<>(component, qualifiers);
    }

    public static <T> Instance<T> of(T component, Consumer<QualifierSetInfo.Builder> builderConsumer) {
        var builder = QualifierSetInfo.builder();
        builderConsumer.accept(builder);
        return new Instance<>(component, builder.build());
    }

    public T getComponent() {
        return component;
    }

    public QualifierSetInfo getQualifiers() {
        return qualifiers;
    }
}
