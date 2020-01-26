/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Template("<start>#REPEAT#$INDEX$$,$#END#<end>")
class FileGeneratorSimpleTest extends FileGenerator {

    String comma = "";
    int index;

    @Test
    void generate() {
        var result = new StringWriter(200);
        var out = new PrintWriter(result);
        super.generate(out);
        assertEquals("<start>1, 2, 3, 4, 5<end>", result.toString());
    }

    @Template(name = "REPEAT")
    public void repeat(BodyProcessor body) {
        var count = 5;
        for (int i = 1; i <= count; i++) {
            this.index = i;
            this.comma = i == count ? "" : ", ";
            body.process();
        }
    }

    @Template(name = "INDEX")
    public String index() {
        return String.valueOf(index);
    }

    @Template(name = ",")
    public String comma() {
        return comma;
    }
}