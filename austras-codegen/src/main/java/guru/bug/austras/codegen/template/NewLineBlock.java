/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.codegen.template;

import java.io.PrintWriter;

class NewLineBlock implements Block {
    static final Block INSTANCE = new NewLineBlock();

    private NewLineBlock() {
    }

    @Override
    public void process(PrintWriter out, ClassTemplateCaller caller) {
        out.println();
    }
}
