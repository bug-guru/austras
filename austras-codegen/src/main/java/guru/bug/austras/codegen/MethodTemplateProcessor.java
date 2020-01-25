/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.codegen;

import guru.bug.austras.codegen.template.ClassTemplateCaller;
import guru.bug.austras.codegen.template.CompiledTemplate;
import guru.bug.austras.codegen.template.TemplateException;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

class MethodTemplateProcessor {
    private final String name;
    private final MethodCaller methodInvoker;

    private MethodTemplateProcessor(String name, MethodCaller methodInvoker) {
        this.name = name;
        this.methodInvoker = methodInvoker;
    }

    static MethodTemplateProcessor with(String name, CompiledTemplate template, Method method) {
        MethodCaller methodCaller;
        var isVoid = Void.TYPE.equals(method.getReturnType()) || method.getReturnType() == null;
        var hasParam = method.getParameterCount() == 1;
        var isRunnableParam = hasParam && Runnable.class.equals(method.getParameters()[0].getType());
        if (!hasParam && !isVoid && template == null) {
            methodCaller = (instance, out, caller, bodyWriter) -> {
                if (bodyWriter != null) {
                    throw new TemplateException("Block " + name + " has a body, but method " + method + " returns a value");
                }
                var result = method.invoke(instance);
                out.print(result);
            };
        } else if (isVoid && isRunnableParam && template == null) {
            methodCaller = (instance, out, caller, bodyWriter) -> {
                if (bodyWriter == null) {
                    throw new TemplateException("Block " + name + " doesn't have template");
                }
                method.invoke(instance, (Runnable) () -> bodyWriter.accept(out));
            };
        } else if (isVoid && isRunnableParam && template != null) {
            methodCaller = (instance, out, caller, bodyWriter) -> {
                if (bodyWriter != null) {
                    throw new TemplateException("Block " + name + " has template defined twice");
                }
                method.invoke(instance, (Runnable) () -> template.process(out, caller));
            };
        } else {
            throw new IllegalArgumentException("Method not supported " + method);
        }
        return new MethodTemplateProcessor(name, methodCaller);
    }

    void process(FileGenerator instance, PrintWriter out, ClassTemplateCaller caller, Consumer<PrintWriter> bodyWriter) {
        try {
            methodInvoker.call(instance, out, caller, bodyWriter);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getName() {
        return name;
    }

    private interface MethodCaller {
        void call(FileGenerator instance, PrintWriter out, ClassTemplateCaller caller, Consumer<PrintWriter> bodyWriter) throws InvocationTargetException, IllegalAccessException;
    }

}