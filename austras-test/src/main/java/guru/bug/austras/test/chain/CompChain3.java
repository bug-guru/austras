/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.test.chain;

@SuppressWarnings("ALL") // this class is for testing only
public class CompChain3 {
    private final CompChain2 chain2; //NOSONAR for testing purposes only

    public CompChain3(CompChain2 chain2) {
        this.chain2 = chain2;
    }
}
