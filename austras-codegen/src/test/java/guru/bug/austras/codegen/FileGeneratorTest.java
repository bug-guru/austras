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
class FileGeneratorTest extends FileGenerator {

    String comma = "";
    int index;

    @Test
    void generate() {
        var result = new StringWriter(200);
        var out = new PrintWriter(result);
        super.generate(out);
        assertEquals("<start>i=1i=1, i=2i=2, i=3i=3, i=4i=4, i=5i=5<end>", result.toString());
    }

    @Template(name = "REPEAT")
    public void repeat(Runnable body) {
        var count = 5;
        for (int i = 1; i <= count; i++) {
            this.index = i;
            this.comma = i == count ? "" : ", ";
            body.run();
        }
    }

    @Template(name = "INDEX", value = "i=$IDX$")
    public void index(Runnable body) {
        body.run();
        body.run();
    }

    @Template(name = "IDX")
    public int idx() {
        return index;
    }

    @Template(name = ",")
    public String comma() {
        return comma;
    }
}