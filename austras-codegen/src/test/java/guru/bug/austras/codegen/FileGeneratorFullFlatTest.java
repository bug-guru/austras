/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.codegen;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Template("<start>#REPEAT#$INDEX$$SWITCH$$,$#END#<end>")
class FileGeneratorFullFlatTest extends FileGenerator {

    String comma = "";
    int index;

    @Test
    void generate() {
        var result = new StringWriter(200);
        var out = new PrintWriter(result);
        super.generate(out);
        assertEquals("<start>|i=1|1=i|one, |i=2|2=i|two, |i=3|3=i|three, |i=4|4=i|four, |i=5|5=i|four+one<end>", result.toString());
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

    @Template(name = "SWITCH")
    public void switchTemplate(BodyProcessor bodyProcessor) {
        bodyProcessor.process(String.valueOf(index));
    }

    @Template(name = "1")
    public String one() {
        return "one";
    }

    @Template(name = "2")
    public String two() {
        return "two";
    }

    @Template(name = "3")
    public String three() {
        return "three";
    }

    @Template(name = "4")
    public String four() {
        return "four";
    }

    @Template(name = "5", value = "$4$+$1$")
    public void five(BodyProcessor body) {
        body.process();
    }

    @Template(name = ",")
    public String comma() {
        return comma;
    }
}