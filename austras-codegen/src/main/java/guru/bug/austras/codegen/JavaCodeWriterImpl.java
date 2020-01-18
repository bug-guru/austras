/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import java.io.PrintWriter;
import java.io.Writer;

class JavaCodeWriterImpl implements JavaCodeWriter {
    private final ImportsManager importsManager;
    private final PrintWriter out;

    JavaCodeWriterImpl(ImportsManager importsManager, Writer out) {
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

}
