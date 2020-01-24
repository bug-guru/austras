/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import guru.bug.austras.codegen.template.CompiledTemplate;
import guru.bug.austras.codegen.template.TemplateCaller;
import guru.bug.austras.codegen.template.TemplateException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public abstract class FileGenerator {
    private static final Map<Class<?>, ClassTemplate> templates = new ConcurrentHashMap<>();

    private ClassTemplate createClassTemplate(Class<?> backedClass, ClassTemplate parent) {
        var rootTemplate = rootTemplate(backedClass);
        var methodTemplates = new ArrayList<MethodTemplate>();
        for (var m : backedClass.getMethods()) {
            var templateAnnotation = m.getDeclaredAnnotation(Template.class);
            if (templateAnnotation == null) {
                continue;
            }
            var templateName = templateAnnotation.name();
            if (templateName.isBlank()) {
                throw new TemplateException("Annotation @Template isn't valid on " + backedClass + "." + m.getName() + ": name is required");
            }
            var template = methodTemplate(backedClass, templateAnnotation);
            methodTemplates.add(template);
        }
        return new ClassTemplate(backedClass, parent, rootTemplate, methodTemplates);
    }

    private MethodTemplate methodTemplate(Class<?> cls, Template templateAnnotation) {
        String templateValue;
        if (!templateAnnotation.file().isBlank()) {
            templateValue = readFromResource(cls, templateAnnotation.file());
        } else if (!templateAnnotation.value().isBlank()) {
            templateValue = templateAnnotation.value();
        } else {
            return null;
        }
        return MethodTemplate.compile(templateValue);
    }

    private CompiledTemplate rootTemplate(Class<?> cls) {
        var templateAnnotation = cls.getDeclaredAnnotation(Template.class);
        String templateValue;
        if (templateAnnotation == null) {
            return CompiledTemplate.extension();
        } else if (!templateAnnotation.file().isBlank()) {
            templateValue = readFromResource(cls, templateAnnotation.file());
        } else if (!templateAnnotation.value().isBlank()) {
            templateValue = templateAnnotation.value();
        } else {
            throw new TemplateException("Annotation @Template isn't valid on " + cls + ": file or value is expected");
        }
        return CompiledTemplate.compile(templateValue);
    }

    private String readFromResource(Class<?> cls, String resourceName) {
        try (var is = getClass().getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new TemplateException(resourceName + " not found for " + getClass().getName());
            }
            try (var reader = new InputStreamReader(is)) {
                var writer = new StringWriter(2048);
                reader.transferTo(writer);
                return writer.toString();
            }
        } catch (IOException e) {
            throw new TemplateException(e);
        }
    }

    protected final void generate(PrintWriter out) {
        var templateStack = collectTemplateStack();
        var callers = new ArrayList<TemplateCaller>();
        TemplateCaller prev = null;
        for (var t : templateStack) {
            var caller =
        }
    }

    private ClassTemplate findTemplate() {
        var classes = new LinkedList<Class<?>>();
        Class<?> cur = getClass();
        while (!cur.equals(FileGenerator.class)) {
            classes.addFirst(cur);
            cur = cur.getSuperclass();
        }

        ClassTemplate parent = null;
        for (var cls : classes) {
            parent = createClassTemplate(cls, parent);
        }

        return parent;
    }


}
