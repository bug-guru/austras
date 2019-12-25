package guru.bug.austras.web.apt;

import guru.bug.austras.apt.core.common.model.DependencyModel;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.codegen.BodyBlock;
import guru.bug.austras.codegen.FromTemplate;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.web.Endpoint;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.PathSplitter;
import guru.bug.austras.web.apt.model.MethodParam;
import guru.bug.austras.web.apt.model.PathItemRef;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FromTemplate("EndpointHandler.java.txt")
public class EndpointHandlerGenerator extends JavaGenerator {
    private final ProcessingContext ctx;
    private String packageName;
    private String simpleClassName;
    private String httpMethod;
    private List<PathItemRef> pathItems;
    private List<MediaType> produces;
    private List<MediaType> accept;
    private boolean commaRequired;
    private PathItemRef currentPathItem;
    private MediaType currentMediaType;
    private DependencyModel controllerProvider;
    private Set<DependencyModel> dependencies;
    private List<MethodParam> methodParams;
    private DependencyModel currentDependency;
    private String endpointMethodName;
    private MethodParam currentMethodParam;
    private int successStatus;
    private DependencyModel resultConverter;

    protected EndpointHandlerGenerator(ProcessingContext ctx) throws IOException, TemplateException {
        super(ctx.processingEnv().getFiler());
        this.ctx = ctx;
    }

    public void generate(ExecutableElement methodElement) {
        this.dependencies = new HashSet<>();
        this.methodParams = new ArrayList<>();
        this.endpointMethodName = methodElement.getSimpleName().toString();
        var cls = (TypeElement) methodElement.getEnclosingElement();
        this.packageName = ctx.processingEnv().getElementUtils().getPackageOf(cls).getQualifiedName().toString();
        this.simpleClassName = cls.getSimpleName().toString() + "_" + methodElement.getSimpleName().toString() + "_EndpointHandler";
        var endpointAnnotation = methodElement.getAnnotation(Endpoint.class);
        this.successStatus = endpointAnnotation.successStatus();
        processControllerProvider(cls);
        processEndpointInfo(endpointAnnotation);
        processParams(methodElement);
        processResult(methodElement);

        generateJavaClass();
    }

    private void processResult(ExecutableElement methodElement) {
        var returnType = methodElement.getReturnType();
        if (returnType.getKind() == TypeKind.VOID) {
            resultConverter = null;
        } else {
            resultConverter = JsonConverterUtil.selectConverter(returnType);
            dependencies.add(resultConverter);
        }
    }

    public void processControllerProvider(TypeElement cls) {
        this.controllerProvider = ctx.modelUtils()
                .createDependencyModel((DeclaredType) cls.asType(), cls);
        dependencies.add(this.controllerProvider);
    }

    private void processParams(ExecutableElement methodElement) {
//        for (var param : methodElement.getParameters()) {
//            var type = param.asType();
//            var name = param.getSimpleName().toString();
//            var converterProvider = StringConverterUtil.selectConverter(type);
//            if (converterProvider != null) {
//                dependencies.add(converterProvider);
//            }
//            var paramObj = new PathParamMethodParam(name, converterProvider);
//            methodParams.add(paramObj);
//        }
    }

    public void processEndpointInfo(Endpoint endpointAnnotation) {
        this.httpMethod = endpointAnnotation.method().toUpperCase();
        this.pathItems = PathSplitter.split(PathItemRef::new, endpointAnnotation.path());
        this.accept = Stream.of(endpointAnnotation.accept())
                .map(MediaType::valueOf)
                .collect(Collectors.toList());
        this.produces = Stream.of(endpointAnnotation.produce())
                .map(MediaType::valueOf)
                .collect(Collectors.toList());
    }

    @FromTemplate("DEPENDENCIES")
    public void dependencies(PrintWriter out, BodyBlock bodyBlock) {
        var i = dependencies.iterator();
        while (i.hasNext()) {
            this.currentDependency = i.next();
            this.commaRequired = i.hasNext();
            out.print(bodyBlock.evaluateBody());
        }
    }

    @FromTemplate("DEPENDENCY_QUALIFIERS")
    public String dependencyQualifiers() {
        return qualifierToString(currentDependency.getQualifiers());
    }

//    @FromTemplate("DEPENDENCY_TYPE")
//    public String dependencyType() {
//        return tryImport(currentDependency.asTypeDeclaration());
//    }

//    @FromTemplate("DEPENDENCY_NAME")
//    public String dependencyName() {
//        return currentDependency.getName();
//    }

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

    @FromTemplate("CONSUMED_TYPES")
    public void consumingTypes(PrintWriter out, BodyBlock body) {
        var mti = accept.iterator();
        while (mti.hasNext()) {
            this.currentMediaType = mti.next();
            this.commaRequired = mti.hasNext();
            out.print(body.evaluateBody());
        }
    }

    @FromTemplate("PRODUCED_TYPES")
    public void producingTypes(PrintWriter out, BodyBlock body) {
        var mti = produces.iterator();
        while (mti.hasNext()) {
            this.currentMediaType = mti.next();
            this.commaRequired = mti.hasNext();
            out.print(body.evaluateBody());
        }
    }

    @FromTemplate("MEDIA_TYPE")
    public String getCurrentMediaType() {
        return currentMediaType.toString();
    }

    @FromTemplate("ENDPOINT_METHOD")
    public String getEndpointMethodName() {
        return endpointMethodName;
    }

//    @FromTemplate("CONTROLLER_PROVIDER_NAME")
//    public String getControllerProviderName() {
//        return controllerProvider.getName();
//    }

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

    @FromTemplate("VAR RESULT =")
    public String getResultVarAssignment() {
        if (resultConverter == null) {
            return "";
        }
        return "var _result = ";
    }
//
//    @FromTemplate("WRITE RESULT")
//    public String getReturnResult() {
//        if (resultConverter == null) {
//            return "";
//        }
//
//        return String.format("%s.toJson(_result, %s.newInstance(_out));",
//                resultConverter.getName(),
//                tryImport(JsonValueWriter.class.getName()));
//    }

    @FromTemplate("SET CONTENT TYPE")
    public String getContentTYpe() {
        if (resultConverter == null) {
            return "";
        }
        return String.format("_response.setContentType(\"%s\");", MediaType.APPLICATION_JSON);
    }

    @FromTemplate("SET CHARSET")
    public String getCharset() {
        if (resultConverter == null) {
            return "";
        }
        return String.format("_response.setCharacterEncoding(\"%s\");", StandardCharsets.UTF_8.name());
    }


}

