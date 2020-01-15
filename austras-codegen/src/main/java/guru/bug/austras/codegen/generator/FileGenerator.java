/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen.generator;

import guru.bug.austras.codegen.Template;

import java.io.PrintWriter;
import java.util.ArrayList;


public abstract class FileGenerator {

    public FileGenerator() {
        var templates = new ArrayList<TemplateHolder>();
        Class<?> cur = getClass();
        while (!cur.equals(FileGenerator.class)) {

            cur = cur.getSuperclass();
        }
    }

    protected void generate(PrintWriter out) {
        Template template = this.getClass().getDeclaredAnnotation(Template.class);
    }

    private static class TemplateHolder {
        final
    }
}
