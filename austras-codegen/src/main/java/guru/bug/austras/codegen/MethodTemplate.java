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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class MethodTemplate {
    private final String name;
    private final MethodCaller methodInvoker;

    private MethodTemplate(String name, MethodCaller methodInvoker) {
        this.name = name;
        this.methodInvoker = methodInvoker;
    }

    static MethodTemplate with(String name, CompiledTemplate template, Method method) {
        if (method.getParameterCount() == 0) {
            return new MethodTemplate(name, (instance, out, caller) -> {
                method.invoke(instance);
                template.process(out, caller);
            });
        }
        if (method.getParameterCount() == 1 && Runnable.class.equals(method.getParameters()[0].getType())) {
            return new MethodTemplate(name, (instance, out, caller) -> {
                method.invoke(instance, (Runnable) () -> template.process(out, caller));
            });
        }
        throw new IllegalArgumentException("Method not supported " + method);
    }

    void call(FileGenerator instance, PrintWriter out, TemplateCaller caller) {
        try {
            methodInvoker.call(instance, out, caller);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getName() {
        return name;
    }

    private interface MethodCaller {
        void call(FileGenerator instance, PrintWriter out, TemplateCaller caller) throws InvocationTargetException, IllegalAccessException;
    }

}