/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Template(file = "inheritance_parent.txt")
public class FileGeneratorInheritanceParent extends FileGenerator {
    private final List<String> lines = new ArrayList<>();

    @Template(name = "COLLECTION")
    public void printCollection(PrintWriter out) {
        for (var l : lines) {
            out.println(l);
        }
    }

    @Template(name = "LINES")
    public void collectLines(BodyProcessor body) {
        body.processAndGetBody().lines()
                .forEach(lines::add);
    }

    @Override
    protected void generate(PrintWriter out) {
        lines.clear();
        super.generate(new PrintWriter(Writer.nullWriter()));
        super.generate(out);
    }
}
