/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template;

import java.io.PrintWriter;

class ExtensionValueBlock implements Block {
    static final Block INSTANCE = new ExtensionValueBlock();

    private ExtensionValueBlock() {
    }

    @Override
    public void process(PrintWriter out, ClassTemplateCaller caller) {
        caller.callExtension(out);
    }
}
