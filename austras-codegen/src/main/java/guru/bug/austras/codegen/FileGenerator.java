/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import guru.bug.austras.codegen.template.CompiledTemplate;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public abstract class FileGenerator {
    private static final Map<Class<?>, TemplateHolder> templates = new ConcurrentHashMap<>();

    public FileGenerator() {
        var toCheck = new LinkedList<Class<?>>();
        Class<?> cur = getClass();
        while (!cur.equals(FileGenerator.class)) {
            toCheck.addFirst(cur);
            cur = cur.getSuperclass();
        }
        var ref = new MutableRef<TemplateHolder>();
        for (Class<?> cls : toCheck) {
            var holder = templates.computeIfAbsent(cls, c -> this.createTemplateHolder(c, ref.value));
            ref.value = holder;
        }
    }

    private TemplateHolder createTemplateHolder(Class<?> cls, TemplateHolder parent) {
        var templateAnnotation = cls.getDeclaredAnnotation(Template.class);
        String templateValue;
        if (templateAnnotation == null) {
            templateValue = "$$";
        } else if (templateAnnotation.file() != null) {
            templateValue = readFromResource(cls, templateAnnotation.file());
        } else if (templateAnnotation.value() != null) {
            templateValue = templateAnnotation.value();
        } else {
            throw new IllegalArgumentException("Annotation @Template isn't valid on " + cls + ". Property 'file' or 'value' is expected");
        }
        var template = CompiledTemplate.compile(templateValue);
    }

    private String readFromResource(Class<?> cls, String resName) {

    }

    protected void generate(PrintWriter out) {
    }

    private static class TemplateHolder {
        final
    }

    private static class MutableRef<T> {
        T value;
    }
}
