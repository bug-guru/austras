/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import guru.bug.austras.codegen.template.CompiledTemplate;
import guru.bug.austras.codegen.template.TemplateCaller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

class ClassTemplate {
    final Class<?> backedClass;
    final ClassTemplate parent;
    final CompiledTemplate rootTemplate;
    final Map<String, MethodTemplate> methodTemplates;

    ClassTemplate(Class<?> backedClass, ClassTemplate parent, CompiledTemplate rootTemplate, List<MethodTemplate> methodTemplates) {
        this.backedClass = backedClass;
        this.parent = parent;
        this.rootTemplate = rootTemplate;
        this.methodTemplates = methodTemplates.stream()
                .collect(Collectors.toMap(MethodTemplate::getName, Function.identity()));
    }

    private void process(FileGenerator generatorInstance, PrintWriter out, Consumer<PrintWriter> childProcessor) {
        if (parent == null) {
            call(generatorInstance, out, childProcessor);
        } else {
            parent.process(generatorInstance, out, w -> call(generatorInstance, w, childProcessor));
        }
    }

    private void call(FileGenerator generatorInstance, PrintWriter out, Consumer<PrintWriter> childProcessor) {
        rootTemplate.process(out, new TemplateCaller() {
            @Override
            public void call(String name, PrintWriter out, Consumer<PrintWriter> bodyWriter) {
                var methodTemplate = methodTemplates.get(name);
                methodTemplate.call(generatorInstance, out, this);
            }

            @Override
            public void callExtension(PrintWriter out) {
                childProcessor.accept(out);
            }
        });
    }
}
