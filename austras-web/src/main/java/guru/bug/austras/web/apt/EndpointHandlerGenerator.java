/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.web.apt;

import guru.bug.austras.apt.core.common.model.DependencyModel;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.codegen.BodyBlock;
import guru.bug.austras.codegen.FromTemplate;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.web.*;
import guru.bug.austras.web.apt.model.*;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FromTemplate("EndpointHandler.java.txt")
public class EndpointHandlerGenerator extends JavaGenerator {
    private final ProcessingContext ctx;
    private String packageName;
    private String simpleClassName;
    private String httpMethod;
    private List<PathItemRef> pathItems;
    private boolean commaRequired;
    private PathItemRef currentPathItem;
    private DependencyRef responseConverterSelectorDependency;
    private DependencyRef controllerDependency;
    private DependencyRef requestBodyConverterSelectorDependency;
    private Map<DependencyModel, DependencyRef> dependencies;
    private List<MethodParam> methodParams;
    private DependencyRef currentDependency;
    private String endpointMethodName;
    private MethodParam currentMethodParam;
    private int successStatus;

    protected EndpointHandlerGenerator(ProcessingContext ctx) throws IOException, TemplateException {
        super(ctx.processingEnv().getFiler());
        this.ctx = ctx;
    }

    public void generate(ExecutableElement methodElement) {
        this.responseConverterSelectorDependency = null;
        this.requestBodyConverterSelectorDependency = null;
        this.dependencies = new HashMap<>();
        this.methodParams = new ArrayList<>();
        this.endpointMethodName = methodElement.getSimpleName().toString();
        var cls = (TypeElement) methodElement.getEnclosingElement();
        this.packageName = ctx.processingEnv().getElementUtils().getPackageOf(cls).getQualifiedName().toString();
        this.simpleClassName = cls.getSimpleName().toString() + "_" + methodElement.getSimpleName().toString() + "_EndpointHandler";
        var endpointAnnotation = methodElement.getAnnotation(Endpoint.class);
        this.successStatus = endpointAnnotation.successStatus();
        processController(cls);
        processEndpointInfo(endpointAnnotation);
        processParams(methodElement);
        processResult(methodElement);

        generateJavaClass();
    }

    public void processController(TypeElement cls) {
        var dep = ctx.modelUtils().createDependencyModel((DeclaredType) cls.asType(), cls);
        this.controllerDependency = addDependencyIfAbsent(new DependencyRef("controller", dep));
    }

    public void processEndpointInfo(Endpoint endpointAnnotation) {
        this.httpMethod = endpointAnnotation.method().toUpperCase();
        this.pathItems = PathSplitter.split(PathItemRef::new, endpointAnnotation.path());
    }

    private void processParams(ExecutableElement methodElement) {
        for (var param : methodElement.getParameters()) {
            MethodParam paramRef;
            if (param.getAnnotation(PathParam.class) != null) {
                paramRef = createPathParam(param);
            } else if (param.getAnnotation(QueryParam.class) != null) {
                paramRef = createQueryParam(param);
            } else if (param.getAnnotation(HeaderParam.class) != null) {
                paramRef = createHeaderParam(param);
            } else if (param.getAnnotation(BodyParam.class) != null) {
                paramRef = createBodyParam(param);
            } else {
                ctx.processingEnv().getMessager().printMessage(Diagnostic.Kind.ERROR, "Unsupported parameter. Must be one of @QueryParam, @PathParam, @HeaderParam or @BodyParam", param);
                return;
            }
            methodParams.add(paramRef);
        }
    }

    private MethodParam createPathParam(VariableElement param) {
        var annotation = param.getAnnotation(PathParam.class);
        var type = param.asType();
        var name = annotation.value();
        if (name.isEmpty()) {
            name = param.getSimpleName().toString();
        }
        var converterRef = addDependencyIfAbsent(ContentConverterUtil.createStringConverterDependency(type));
        return new PathParamMethodParam(name, converterRef);
    }

    private MethodParam createQueryParam(VariableElement param) {
        var annotation = param.getAnnotation(QueryParam.class);
        var type = param.asType();
        var name = annotation.value();
        if (name.isEmpty()) {
            name = param.getSimpleName().toString();
        }
        var converterRef = addDependencyIfAbsent(ContentConverterUtil.createStringConverterDependency(type));
        return new QueryParamMethodParam(name, converterRef);
    }

    private MethodParam createHeaderParam(VariableElement param) {
        var annotation = param.getAnnotation(HeaderParam.class);
        var type = param.asType();
        var name = annotation.value();
        if (name.isEmpty()) {
            name = param.getSimpleName().toString();
        }
        var converterRef = addDependencyIfAbsent(ContentConverterUtil.createStringConverterDependency(type));
        return new HeaderParamMethodParam(name, converterRef);
    }

    private MethodParam createBodyParam(VariableElement param) {
        if (this.requestBodyConverterSelectorDependency != null) {
            ctx.processingEnv().getMessager().printMessage(Diagnostic.Kind.ERROR, "Maximum one @BodyParam is allowed", param);
            return null;
        }
        var annotation = param.getAnnotation(BodyParam.class);
        var type = param.asType();
        var name = annotation.value();
        if (name.isEmpty()) {
            name = param.getSimpleName().toString();
        }
        this.requestBodyConverterSelectorDependency = addDependencyIfAbsent(ContentConverterUtil.createConverterSelectorDependency(type));
        return new BodyParamMethodParam(name, requestBodyConverterSelectorDependency);
    }

    private void processResult(ExecutableElement methodElement) {
        var returnType = methodElement.getReturnType();
        if (returnType.getKind() == TypeKind.VOID) {
            responseConverterSelectorDependency = null;
        } else {
            var ref = ContentConverterUtil.createConverterSelectorDependency(returnType);
            responseConverterSelectorDependency = addDependencyIfAbsent(ref);
        }
    }

    private DependencyRef addDependencyIfAbsent(DependencyRef ref) {
        if (ref == null) {
            return null;
        }
        return dependencies.computeIfAbsent(ref.getDependencyModel(), k -> ref);
    }

    @FromTemplate("DEPENDENCIES")
    public void dependencies(PrintWriter out, BodyBlock bodyBlock) {
        var i = dependencies.values().iterator();
        while (i.hasNext()) {
            this.currentDependency = i.next();
            this.commaRequired = i.hasNext();
            out.print(bodyBlock.evaluateBody());
        }
    }

    @FromTemplate("DEPENDENCY_QUALIFIERS")
    public String dependencyQualifiers() {
        var qualifiers = currentDependency.getDependencyModel().getQualifiers();
        return qualifiers == null ? "" : qualifiers.toString();
    }

    @FromTemplate("DEPENDENCY_TYPE")
    public String dependencyType() {
        return tryImport(currentDependency.getDependencyModel().asTypeDeclaration());
    }

    @FromTemplate("DEPENDENCY_NAME")
    public String dependencyName() {
        return currentDependency.getVarName();
    }

    @FromTemplate(",")
    public String optionalComma() {
        return commaRequired ? ", " : "";
    }

    @FromTemplate("METHOD")
    public String getHttpMethod() {
        return httpMethod;
    }

    @FromTemplate("SUCCESS_STATUS")
    public int getSuccessStatus() {
        return successStatus;
    }

    @FromTemplate("PATH_ITEMS")
    public void pathItems(PrintWriter out, BodyBlock body) {
        var pi = pathItems.iterator();
        while (pi.hasNext()) {
            this.currentPathItem = pi.next();
            this.commaRequired = pi.hasNext();
            out.print(body.evaluateBody());
        }
    }

    @FromTemplate("PATH_ITEM_TYPE")
    public String getCurrentPathItemType() {
        return currentPathItem.getType();
    }

    @FromTemplate("PATH_ITEM_VALUE")
    public String getCurrentPathItemValue() {
        return currentPathItem.getValue();
    }

    @FromTemplate("PACKAGE_NAME")
    @Override
    public String getPackageName() {
        return packageName;
    }

    @FromTemplate("SIMPLE_NAME")
    @Override
    public String getSimpleClassName() {
        return simpleClassName;
    }

    @FromTemplate("ENDPOINT_METHOD")
    public String getEndpointMethodName() {
        return endpointMethodName;
    }

    @FromTemplate("HAS_RESPONSE_BODY")
    public void processIfHasResponseContent(PrintWriter out, BodyBlock body) {
        if (responseConverterSelectorDependency != null) {
            out.print(body.evaluateBody());
        }
    }

    @FromTemplate("HAS_REQUEST_BODY")
    public void processIfHasRequestBody(PrintWriter out, BodyBlock body) {
        if (requestBodyConverterSelectorDependency != null) {
            out.print(body.evaluateBody());
        }
    }

    @FromTemplate("RESPONSE_CONVERTER_SELECTOR_NAME")
    public String responseConverterSelectorName() {
        return responseConverterSelectorDependency.getVarName();
    }

    @FromTemplate("REQUEST_CONVERTER_SELECTOR_NAME")
    public String requestConverterSelectorName() {
        return requestBodyConverterSelectorDependency.getVarName();
    }

    @FromTemplate("CONTROLLER_DEPENDENCY_NAME")
    public String getControllerProviderName() {
        return controllerDependency.getVarName();
    }

    @FromTemplate("ENDPOINT_METHOD_PARAMS")
    public void endpointMethodParams(PrintWriter out, BodyBlock body) {
        var mpi = methodParams.iterator();
        while (mpi.hasNext()) {
            this.currentMethodParam = mpi.next();
            this.commaRequired = mpi.hasNext();
            out.print(body.evaluateBody());
        }
    }

    @FromTemplate("ENDPOINT_METHOD_PARAM_EXPRESSION")
    public String getEndpointMethodParamExpression() {
        return currentMethodParam.expresion();
    }


}

