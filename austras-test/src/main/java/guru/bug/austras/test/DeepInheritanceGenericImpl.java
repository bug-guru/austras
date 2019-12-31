/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.test;

import guru.bug.austras.core.qualifiers.Default;

@SuppressWarnings("ALL")
@Default
public class DeepInheritanceGenericImpl extends DeepInheritanceGenericAbstract<Boolean> {

    @Override
    protected Boolean anotherSomething() {
        return true;
    }
}
