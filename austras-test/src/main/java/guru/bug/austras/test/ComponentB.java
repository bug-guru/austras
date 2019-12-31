/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.test;

@SuppressWarnings("ALL") // this class is for testing only
public class ComponentB {
    private final ComponentA componentA; //NOSONAR for testing purposes only

    public ComponentB(ComponentA componentA) {
        this.componentA = componentA;
    }
}
