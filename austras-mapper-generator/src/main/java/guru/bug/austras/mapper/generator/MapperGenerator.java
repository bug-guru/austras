/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.mapper.generator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MapperGenerator {


    public static void main(String[] args) throws IOException {
        var dir = args[0];
        var basePath = Paths.get(dir);
        generateNumerics(basePath);
        generateString(basePath);
    }

    private static void generateString(Path basePath) throws IOException {
        for (var cnv : StringCompatible.values()) {
            String imp = null;
            var type = cnv.getType();
            var dotIdx = type.lastIndexOf('.');
            if (dotIdx != -1) {
                imp = type;
                type = type.substring(dotIdx + 1);
            }
            generate(basePath, type, "String", cnv.getToString(), imp);
            generate(basePath, "String", type, cnv.getFromString(), imp);
        }
    }

    private static void generateNumerics(Path basePath) throws IOException {
        for (var from : Numerics.values()) {
            for (var to : Numerics.values()) {
                if (from == to) {
                    continue;
                }
                generate(basePath, from.getBoxed(), to.getBoxed(), "source." + to.getPrimitive() + "Value()");
            }
        }
    }

    private static void generate(Path dir, String from, String to, String conversion, String... imports) throws IOException {
        var clsName = from + "To" + to + "Mapper";
        var pathName = dir.resolve(clsName + ".java");

        try (var os = new FileOutputStream(pathName.toFile());
             var out = new PrintWriter(os)
        ) {
            out.println("package guru.bug.austras.mapper.impl;");
            out.println();

            out.println("import guru.bug.austras.core.qualifiers.Default;");
            out.println("import guru.bug.austras.mapper.Mapper;");
            for (var i : imports) {
                if (i != null) {
                    out.print("import ");
                    out.print(i);
                    out.println(";");
                }
            }
            out.println();

            out.println("@Default");
            out.print("public class ");
            out.print(clsName);
            out.print(" implements Mapper<");
            out.print(from);
            out.print(", ");
            out.print(to);
            out.println("> {");

            out.println("    @Override");

            out.print("    public ");
            out.print(to);
            out.print(" map(");
            out.print(from);
            out.println(" source) {");

            out.print("        return source == null ? null : ");
            out.print(conversion);
            out.println(";");

            out.println("    }");
            out.println("}");
        }
    }
}
