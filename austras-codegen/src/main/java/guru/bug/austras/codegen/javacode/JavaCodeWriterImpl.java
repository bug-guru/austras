/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.javacode;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.function.Function;

class JavaCodeWriterImpl implements JavaCodeWriter {
    private final Function<String, TemplateProcessor> resolver;
    private final ImportsManager importsManager;
    private final PrintWriter out;

    JavaCodeWriterImpl(Function<String, TemplateProcessor> resolver, ImportsManager importsManager, Writer out) {
        this.resolver = resolver;
        this.importsManager = importsManager;
        this.out = new PrintWriter(out);
    }

    @Override
    public void writeQualifiedName(String qualifiedName) {

    }

    @Override
    public void writeStringLiteral(String str) {

    }

    @Override
    public void writeRaw(String str) {

    }

    @Override
    public void writeTemplate(Template template) {

    }

}
