/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.test;

import guru.bug.austras.core.qualifiers.Qualifier;

@SuppressWarnings("ALL")
@Qualifier(name = "alternative")
public class ComponentEImpl1 implements ComponentE {
    @Override
    public void doit() {
        //NOSONAR only for testing
    }
}
