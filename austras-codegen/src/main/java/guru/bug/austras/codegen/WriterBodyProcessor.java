/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import guru.bug.austras.codegen.template.ClassTemplateCaller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;

class WriterBodyProcessor implements BodyProcessor {
    private final PrintWriter out;
    private final Consumer<PrintWriter> bodyWriter;
    private final ClassTemplateCaller caller;

    WriterBodyProcessor(PrintWriter out, Consumer<PrintWriter> bodyWriter, ClassTemplateCaller caller) {
        this.out = out;
        this.bodyWriter = bodyWriter;
        this.caller = caller;
    }

    @Override
    public void process() {
        bodyWriter.accept(out);
    }

    @Override
    public void process(String name) {
        caller.callMethod(name, out, null);
    }

    @Override
    public String processAndGetBody() {
        var result = new StringWriter(200);
        var pw = new PrintWriter(result);
        bodyWriter.accept(pw);
        return result.toString();
    }

    @Override
    public PrintWriter getOutput() {
        return out;
    }
}
