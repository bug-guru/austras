/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.core;

// TODO application events not yet implemented
public interface ApplicationEvents {
    /**
     * Framework is about to execute all @Service methods. Called by the framework when all dependencies are resolved and all providers were created.
     */
    default void preStart() {

    }

    /**
     * This method called after all @Service methods.
     */
    default void postStart() {

    }
}
