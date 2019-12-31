/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.test.chain;

@SuppressWarnings("ALL") // this class is for testing only
public class CompChain4 {
    private final CompChain3 chain3; //NOSONAR for testing purposes only

    public CompChain4(CompChain3 chain3) {
        this.chain3 = chain3;
    }
}
