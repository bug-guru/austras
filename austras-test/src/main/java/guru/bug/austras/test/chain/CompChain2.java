/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.test.chain;


@SuppressWarnings("ALL") // this class is for testing only
public class CompChain2 {
    private final CompChain1 chain1; //NOSONAR for testing purposes only

    public CompChain2(CompChain1 chain1) {
        this.chain1 = chain1;
    }
}
