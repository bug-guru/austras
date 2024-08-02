/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.test;

import guru.bug.austras.core.qualifiers.Default;

@Default
@SuppressWarnings("ALL") // this class is for testing only
public class ComponentParametrizedUsingInteger {
    private final ComponentParametrized<Integer> t; //NOSONAR for testing purposes only

    public ComponentParametrizedUsingInteger(ComponentParametrized<Integer> t) {

        this.t = t;
    }
}
