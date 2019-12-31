/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@FromTemplate("loop_and_var.txt")
public class GeneratorTest extends Generator {
    private int externalVar;
    private int internalVar;

    public GeneratorTest() throws IOException, TemplateException {
        super();
    }

    @Test
    public void generateTest() throws IOException {
        var out = new StringWriter(1024);
        this.generateTo(out);
        var result = out.toString();
        Assertions.assertEquals("This is first line" + System.lineSeparator() +
                "0.100" + System.lineSeparator() +
                "0.101" + System.lineSeparator() +
                "0.102" + System.lineSeparator() +
                "1.100" + System.lineSeparator() +
                "1.101" + System.lineSeparator() +
                "1.102" + System.lineSeparator() +
                "2.100" + System.lineSeparator() +
                "2.101" + System.lineSeparator() +
                "2.102" + System.lineSeparator() +
                "This is last line" + System.lineSeparator(), result);
    }

    @FromTemplate("EXTERNAL LOOP")
    public void externalLoop(PrintWriter out, BodyBlock body) {
        for (int i = 0; i < 3; i++) {
            externalVar = i;
            out.write(body.evaluateBody());
        }
    }

    @FromTemplate("INTERNAL LOOP")
    public void internalLoop(PrintWriter out, BodyBlock body) {
        for (int i = 100; i < 103; i++) {
            internalVar = i;
            out.write(body.evaluateBody());
        }
    }

    @FromTemplate("INT_VALUE")
    public int getIntVar() {
        return internalVar;
    }

    @FromTemplate("EXT_VALUE")
    public int getExtVar() {
        return externalVar;
    }
}
