/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template;

import java.io.PrintWriter;

class ValueBlock implements Block {

    private final String name;

    ValueBlock(String name) {
        this.name = name;
    }

    @Override
    public void process(PrintWriter out, ClassTemplateCaller caller) {
        caller.callMethod(name, out, null);
    }
}
