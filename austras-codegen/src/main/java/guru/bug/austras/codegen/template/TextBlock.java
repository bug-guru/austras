/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.template;

import java.io.PrintWriter;

class TextBlock implements Block {
    private final String text;

    TextBlock(String text) {
        this.text = text;
    }

    @Override
    public void process(PrintWriter out, TemplateCaller caller) {
        out.print(text);
    }
}
