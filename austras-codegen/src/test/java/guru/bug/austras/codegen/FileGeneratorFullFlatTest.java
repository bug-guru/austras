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
class FileGeneratorFullFlatTest extends FileGenerator {

    String comma = "";
    int index;

    @Test
    void generate() {
        var result = new StringWriter(200);
        var out = new PrintWriter(result);
        super.generate(out);
        assertEquals("<start>|i=1|1=i|, |i=2|2=i|, |i=3|3=i|, |i=4|4=i|, |i=5|5=i|<end>", result.toString());
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

    @Template(name = "INDEX", value = "i=$IDX$")
    public void index(BodyProcessor body) {
        body.getOutput().print("|");
        body.process();
        body.getOutput().print("|");
        var txt = new StringBuilder(body.processAndGetBody());
        body.getOutput().print(txt.reverse());
        body.getOutput().print("|");
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