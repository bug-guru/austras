/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.test;

@SuppressWarnings("ALL") // this class is for testing only
public class ComponentDImpl extends ComponentDAbstract {
    private final ComponentC c; //NOSONAR for testing purposes only

    public ComponentDImpl(ComponentA a, ComponentC c) {
        super(a);
        this.c = c;
    }
}
