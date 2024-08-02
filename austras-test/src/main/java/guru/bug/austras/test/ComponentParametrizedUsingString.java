/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.test;

@SuppressWarnings("ALL") // this class is for testing only
public class ComponentParametrizedUsingString {
    private final ComponentParametrized<String> t; //NOSONAR for testing purposes only

    public ComponentParametrizedUsingString(ComponentParametrized<String> t) {

        this.t = t;
    }
}
