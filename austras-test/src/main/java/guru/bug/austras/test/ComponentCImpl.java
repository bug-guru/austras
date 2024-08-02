/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.test;

@SuppressWarnings("ALL") // this class is for testing only
public class ComponentCImpl implements ComponentC {

    private final ComponentA a; //NOSONAR only for testing
    private final ComponentB b; //NOSONAR only for testing

    public ComponentCImpl(ComponentA a, ComponentB b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void doSomething() {
        //NOSONAR only for testing
    }
}
