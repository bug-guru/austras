package guru.bug.austras.web.apt;

import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.codegen.BodyBlock;
import guru.bug.austras.codegen.FromTemplate;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.web.Endpoint;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.PathSplitter;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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

    protected EndpointHandlerGenerator(ProcessingContext ctx) throws IOException, TemplateException {
        super(ctx.processingEnv().getFiler());
        this.ctx = ctx;
    }

    public void generate(ExecutableElement methodElement) {
        var cls = (TypeElement) methodElement.getEnclosingElement();
        this.packageName = ctx.processingEnv().getElementUtils().getPackageOf(cls).getQualifiedName().toString();
        this.simpleClassName = cls.getSimpleName().toString() + "_" + methodElement.getSimpleName().toString() + "_EndpointHandler";
        var endpointAnnotation = methodElement.getAnnotation(Endpoint.class);
        this.httpMethod = endpointAnnotation.method().toUpperCase();
        this.pathItems = PathSplitter.split(PathItemRef::new, endpointAnnotation.path());
        this.accept = Stream.of(endpointAnnotation.accept())
                .map(MediaType::valueOf)
                .collect(Collectors.toList());
        this.produces = Stream.of(endpointAnnotation.produce())
                .map(MediaType::valueOf)
                .collect(Collectors.toList());

        generateJavaClass();
    }

    @FromTemplate("DEPENDENCIES")
    public void dependencies(PrintWriter out, BodyBlock bodyBlock) {
        // TODO
    }

    @FromTemplate("DEPENDENCY_QUALIFIERS")
    public String dependencyQualifiers() {
        // TODO
        return "";
    }

    @FromTemplate("DEPENDENCY_TYPE")
    public String dependencyType() {
        // TODO
        return "";
    }

    @FromTemplate("DEPENDENCY_NAME")
    public String dependencyName() {
        // TODO
        return "";
    }

    @FromTemplate(",")
    public String optionalComma() {
        return commaRequired ? ", " : "";
    }

    @FromTemplate("METHOD")
    public String getHttpMethod() {
        return httpMethod;
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
        return currentPathItem.type;
    }

    @FromTemplate("PATH_ITEM_VALUE")
    public String getCurrentPathItemValue() {
        return currentPathItem.value;
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

    private static class PathItemRef {
        final String type;
        final String value;

        PathItemRef(String item) {
            if (item.startsWith("{") && item.endsWith("}")) {
                type = "param";
                value = item.substring(1, item.length() - 1);
            } else {
                type = "matching";
                value = item;
            }
        }
    }
}

