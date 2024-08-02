/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.test;

@SuppressWarnings("ALL")
public abstract class DeepInheritanceGenericAbstract<T> implements DeepInheritanceGeneric<T> {
    @Override
    public void doSomething(T t) {

    }

    protected abstract T anotherSomething();
}
