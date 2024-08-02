/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
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

@Template(file = "loop_and_var.txt")
public class FileGeneratorWithFileTest extends FileGenerator {
    private int externalVar;
    private int internalVar;

    @Test
    public void generate() throws IOException {
        var writer = new StringWriter(1024);
        var out = new PrintWriter(writer);
        super.generate(out);
        var result = writer.toString();
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

    @Template(name = "EXTERNAL LOOP")
    public void externalLoop(BodyProcessor body) {
        for (int i = 0; i < 3; i++) {
            externalVar = i;
            body.process();
        }
    }

    @Template(name = "INTERNAL LOOP")
    public void internalLoop(BodyProcessor body) {
        for (int i = 100; i < 103; i++) {
            internalVar = i;
            body.process();
        }
    }

    @Template(name = "INT_VALUE")
    public int getIntVar() {
        return internalVar;
    }

    @Template(name = "EXT_VALUE")
    public int getExtVar() {
        return externalVar;
    }
}
