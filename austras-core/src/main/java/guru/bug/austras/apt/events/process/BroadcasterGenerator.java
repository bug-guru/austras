/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.apt.events.process;

import guru.bug.austras.apt.core.common.model.QualifierModel;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.apt.events.model.EventsBroadcasterModel;
import guru.bug.austras.apt.events.model.MethodModel;
import guru.bug.austras.apt.events.model.MethodParam;
import guru.bug.austras.codegen.BodyBlock;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.codegen.Template;
import guru.bug.austras.codegen.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Template(name = "Broadcaster.java.txt")
public class BroadcasterGenerator extends JavaGenerator {

    private final ProcessingContext ctx;
    private EventsBroadcasterModel eventsBroadcasterModel;
    private MethodModel currentMethod;

    BroadcasterGenerator(ProcessingContext ctx) throws IOException, TemplateException {
        super(ctx.processingEnv().getFiler());
        this.ctx = ctx;
    }

    void generate(VariableElement e) {
        eventsBroadcasterModel = createModel(e);
        super.generateJavaClass();
    }

    @Override
    @Template(name = "PACKAGE_NAME")
    public String getPackageName() {
        return eventsBroadcasterModel.getPackageName();
    }

    @Override
    @Template(name = "SIMPLE_CLASS_NAME")
    public String getSimpleClassName() {
        return eventsBroadcasterModel.getSimpleName();
    }

    @Template(name = "EVENTS_INTERFACE")
    public String getMessageType() {
        return tryImport(eventsBroadcasterModel.getEventsInterface());
    }

    @Template(name = "BROADCASTER_QUALIFIERS")
    public String broadcasterQualifiers() {
        return eventsBroadcasterModel.getQualifier().toString();
    }

    @Template(name = "DEPENDENCIES_QUALIFIERS")
    public String dependenciesQualifiers() {
        return eventsBroadcasterModel.getQualifier().minus(QualifierModel.BROADCAST).toString();
    }

    @Template(name = "METHODS")
    public void methods(PrintWriter out, BodyBlock body) {
        for (var m : eventsBroadcasterModel.getMethods()) {
            this.currentMethod = m;
            out.print(body.evaluateBody());
        }
    }

    @Template(name = "METHOD_NAME")
    public String methodName() {
        return currentMethod.getName();
    }

    @Template(name = "METHOD_PARAMS")
    public String methodParams() {
        return currentMethod.getParameters().stream()
                .map(p -> {
                    if (p.isPrimitive()) {
                        return p.getType() + " " + p.getName();
                    } else {
                        return tryImport(p.getType()) + " " + p.getName();
                    }
                })
                .collect(Collectors.joining(", "));
    }

    @Template(name = "METHOD_PARAM_NAMES")
    public String methodParamNames() {
        return currentMethod.getParameters().stream()
                .map(MethodParam::getName)
                .collect(Collectors.joining(", "));
    }

    private EventsBroadcasterModel createModel(VariableElement e) {

        var result = new EventsBroadcasterModel();
        var packageName = ctx.processingEnv().getElementUtils().getPackageOf(e).getQualifiedName().toString();
        result.setPackageName(packageName);

        var dependency = ctx.modelUtils().createDependencyModel(e);
        result.setEventsInterface(dependency.getType());
        result.setQualifier(dependency.getQualifiers());

        var qualifier = ctx.modelUtils().extractQualifiers(e);
        result.setQualifier(qualifier);

        var method = e.getEnclosingElement();
        var clazz = method.getEnclosingElement();
        var paramName = e.getSimpleName().toString();
        var simpleName = clazz.getSimpleName() + StringUtils.capitalize(paramName) + "Broadcaster";
        result.setSimpleName(simpleName);

        result.setMethods(methodsOf(e.asType()));

        return result;
    }

    private List<MethodModel> methodsOf(TypeMirror type) {
        if (type.getKind() != TypeKind.DECLARED) {
            throw new IllegalArgumentException(type.toString());
        }
        var result = new ArrayList<MethodModel>();
        var elem = (TypeElement) ((DeclaredType) type).asElement();
        for (var member : elem.getEnclosedElements()) {
            if (member.getKind() != ElementKind.METHOD) {
                continue;
            }
            var methodElement = (ExecutableElement) member;
            var resultMethod = new MethodModel();
            resultMethod.setName(methodElement.getSimpleName().toString());
            resultMethod.setParameters(collectMethodParams(methodElement));
            result.add(resultMethod);
        }
        return result;
    }

    private List<MethodParam> collectMethodParams(ExecutableElement methodElement) {
        var result = new ArrayList<MethodParam>();
        for (var p : methodElement.getParameters()) {
            var resultParam = new MethodParam();
            var paramType = p.asType();
            resultParam.setName(p.getSimpleName().toString());
            resultParam.setPrimitive(paramType.getKind().isPrimitive());
            resultParam.setType(paramType.toString());
            result.add(resultParam);
        }
        return result;
    }
}
