/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import guru.bug.austras.codegen.template.ClassTemplateCaller;
import guru.bug.austras.codegen.template.CompiledTemplate;

import java.io.PrintWriter;
import java.io.StringWriter;

public class TemplateBodyProcessor implements BodyProcessor {
    private final CompiledTemplate template;
    private final PrintWriter out;
    private final ClassTemplateCaller caller;

    public TemplateBodyProcessor(CompiledTemplate template, PrintWriter out, ClassTemplateCaller caller) {
        this.template = template;
        this.out = out;
        this.caller = caller;
    }

    @Override
    public void process() {
        template.process(out, caller);
    }

    @Override
    public String processAndGetBody() {
        var result = new StringWriter(200);
        var pw = new PrintWriter(result);
        template.process(pw, caller);
        return result.toString();
    }

    @Override
    public PrintWriter getOutput() {
        return out;
    }
}
