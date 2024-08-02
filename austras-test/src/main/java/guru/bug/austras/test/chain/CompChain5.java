/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.test.chain;

import guru.bug.austras.core.qualifiers.Default;

@Default
@SuppressWarnings("ALL") // this class is for testing only
public class CompChain5 {
    private final CompChain4 chain4; //NOSONAR for testing purposes only

    public CompChain5(CompChain4 chain4) {
        this.chain4 = chain4;
    }
}
