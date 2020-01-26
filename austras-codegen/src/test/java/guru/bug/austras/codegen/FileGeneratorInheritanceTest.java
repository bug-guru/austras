/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

@Template(file = "inheritance_last.txt")
public class FileGeneratorInheritanceTest extends FileGeneratorInheritanceMiddle {

    @Test
    public void generate() {
        var writer = new StringWriter();
        var out = new PrintWriter(writer);
        generate(out);
        var n = System.lineSeparator();
        Assertions.assertEquals("start parent" + n +
                                "parent line 1" + n +
                                "parent line 2" + n +
                                "parent line 3" + n +
                                "middle line 1" + n +
                                "middle line 2" + n +
                                "last line 1" + n +
                                "last line 2" + n +
                                n +
                                "start middle" + n +
                                "start last" + n +
                                "end last" + n +
                                "end middle" + n +
                                "end parent", writer.toString());
    }

}
