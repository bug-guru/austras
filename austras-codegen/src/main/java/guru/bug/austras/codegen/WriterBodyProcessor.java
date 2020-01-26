/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;

class WriterBodyProcessor implements BodyProcessor {
    private final PrintWriter out;
    private final Consumer<PrintWriter> bodyWriter;

    WriterBodyProcessor(PrintWriter out, Consumer<PrintWriter> bodyWriter) {
        this.out = out;
        this.bodyWriter = bodyWriter;
    }

    @Override
    public void process() {
        bodyWriter.accept(out);
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
