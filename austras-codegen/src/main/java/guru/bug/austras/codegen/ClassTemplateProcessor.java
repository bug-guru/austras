/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import guru.bug.austras.codegen.template.ClassTemplateCaller;
import guru.bug.austras.codegen.template.CompiledTemplate;
import guru.bug.austras.codegen.template.TemplateException;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

class ClassTemplateProcessor {
    final Class<?> backedClass;
    final ClassTemplateProcessor parent;
    final CompiledTemplate rootTemplate;
    final Map<String, MethodTemplateProcessor> methodTemplateProcessors;

    ClassTemplateProcessor(Class<?> backedClass,
                           ClassTemplateProcessor parent,
                           CompiledTemplate rootTemplate,
                           List<MethodTemplateProcessor> methodTemplateProcessors) {
        this.backedClass = backedClass;
        this.parent = parent;
        this.rootTemplate = rootTemplate;
        this.methodTemplateProcessors = methodTemplateProcessors.stream()
                .collect(Collectors.toUnmodifiableMap(MethodTemplateProcessor::getName, Function.identity()));
    }

    void process(FileGenerator generatorInstance, PrintWriter out) {
        processOrForwardToParent(generatorInstance, out, w -> {
            throw new TemplateException("No children template");
        });
    }

    private void processOrForwardToParent(FileGenerator generatorInstance,
                                          PrintWriter out,
                                          Consumer<PrintWriter> childProcessor) {
        if (parent == null) {
            processThis(generatorInstance, out, childProcessor);
        } else {
            parent.processOrForwardToParent(generatorInstance, out,
                    w -> processThis(generatorInstance, w, childProcessor));
        }
    }

    private MethodTemplateProcessor findMethodProcessor(String name) {
        Objects.requireNonNull(name, "name");
        var result = methodTemplateProcessors.get(name);
        if (result == null && parent != null) {
            result = parent.findMethodProcessor(name);
        }
        if (result == null) {
            throw new TemplateException("Template method [" + name + "] not found");
        }
        return result;
    }

    private void processThis(FileGenerator generatorInstance, PrintWriter out, Consumer<PrintWriter> childProcessor) {
        rootTemplate.process(out, new ClassTemplateCaller() {

            @Override
            public void callMethod(String name, PrintWriter out, Consumer<PrintWriter> bodyWriter) {
                var methodTemplateProcessor = findMethodProcessor(name);
                methodTemplateProcessor.process(generatorInstance, out, this, bodyWriter);
            }

            @Override
            public void callExtension(PrintWriter out) {
                childProcessor.accept(out);
            }
        });
    }
}
