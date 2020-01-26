/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template;

import java.io.PrintWriter;

public interface Block {
    void process(PrintWriter out, ClassTemplateCaller caller);
}