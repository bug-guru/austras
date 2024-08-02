/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import guru.bug.austras.codegen.template.CompiledTemplate;
import guru.bug.austras.codegen.template.TemplateException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.*;


public abstract class FileGenerator {
    private static final Map<Class<?>, ClassTemplateProcessor> templates = new HashMap<>();

    public FileGenerator() {
        Class<?> cur = getClass();
        if (!templates.containsKey(cur)) {
            var classes = new LinkedList<Class<?>>();
            while (!cur.equals(FileGenerator.class)) {
                classes.addFirst(cur);
                if (templates.containsKey(cur)) {
                    break;
                }
                cur = cur.getSuperclass();
            }

            ClassTemplateProcessor parent = null;
            for (var cls : classes) {
                var tmp = templates.get(cls);
                if (tmp == null) {
                    tmp = createClassTemplateProcessor(cls, parent);
                    templates.put(cls, tmp);
                }
                parent = tmp;
            }
        }
    }

    private ClassTemplateProcessor createClassTemplateProcessor(Class<?> backedClass, ClassTemplateProcessor parentProcessor) {
        var rootTemplate = compileRootTemplate(backedClass);
        var methodTemplateProcessors = new ArrayList<MethodTemplateProcessor>();
        for (var m : backedClass.getDeclaredMethods()) {
            createMethodTemplateProcessor(backedClass, m).ifPresent(methodTemplateProcessors::add);
        }
        return new ClassTemplateProcessor(backedClass, parentProcessor, rootTemplate, methodTemplateProcessors);
    }

    private CompiledTemplate compileRootTemplate(Class<?> cls) {
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

    private Optional<MethodTemplateProcessor> createMethodTemplateProcessor(Class<?> cls, Method method) {
        var templateAnnotation = method.getDeclaredAnnotation(Template.class);
        if (templateAnnotation == null) {
            return Optional.empty();
        }
        CompiledTemplate template;
        var templateName = templateAnnotation.name();
        if (templateName.isBlank()) {
            throw new TemplateException("Annotation @Template isn't valid on " + method + " (" + cls + ") : name is required");
        }

        if (!templateAnnotation.file().isBlank()) {
            var txt = readFromResource(cls, templateAnnotation.file());
            template = CompiledTemplate.compile(txt);
        } else if (!templateAnnotation.value().isBlank()) {
            var txt = templateAnnotation.value();
            template = CompiledTemplate.compile(txt);
        } else {
            template = null;
        }

        return Optional.of(MethodTemplateProcessor.with(templateName, template, method));
    }

    private String readFromResource(Class<?> cls, String resourceName) {
        try (var is = cls.getResourceAsStream(resourceName)) {
            if (is == null) {
                throw new TemplateException(resourceName + " not found for " + cls.getName());
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

    protected void generate(PrintWriter out) {
        var template = templates.get(getClass());
        template.process(this, out);
    }

}
