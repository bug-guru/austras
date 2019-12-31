/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.core;

import guru.bug.austras.core.qualifiers.QualifierSetInfo;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Selector<E> {
    private final List<Instance<? extends E>> components;

    private Selector(List<Instance<? extends E>> components) {
        this.components = components;
    }

    @SafeVarargs
    public static <E> Selector<E> of(Instance<? extends E>... components) {
        return new Selector<>(List.of(components));
    }

    public Collection<E> get() {
        return components.stream()
                .map(Instance::getComponent)
                .collect(Collectors.toUnmodifiableList());
    }

    public Collection<E> get(Predicate<QualifierSetInfo> filter) {
        return components.stream()
                .filter(p -> filter.test(p.getQualifiers()))
                .map(Instance::getComponent)
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<E> any(Predicate<QualifierSetInfo> filter) {
        return components.stream()
                .filter(p -> filter.test(p.getQualifiers()))
                .map(Instance::getComponent)
                .findAny()
                .map(v -> (E) v);
    }

    public E single(Predicate<QualifierSetInfo> filter) {
        var tmp = components.stream()
                .filter(p -> filter.test(p.getQualifiers()))
                .map(Instance::getComponent)
                .limit(2)
                .collect(Collectors.toList());
        if (tmp.isEmpty()) {
            throw new NoSuchElementException();
        }
        if (tmp.size() > 1) {
            throw new IllegalStateException();
        }
        return tmp.get(0);
    }

}
