/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import java.io.PrintWriter;

public interface BodyProcessor {

    void process();

    void process(String name);

    String processAndGetBody();

    PrintWriter getOutput();

}
