package guru.bug.austras.web.apt;

import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.apt.core.model.DependencyModel;
import guru.bug.austras.codegen.BodyBlock;
import guru.bug.austras.codegen.FromTemplate;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.convert.converters.*;
import guru.bug.austras.web.Endpoint;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.PathSplitter;
import guru.bug.austras.web.apt.model.PathItemRef;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
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
        processEndpointInfo(endpointAnnotation);
        processParams(methodElement);

        generateJavaClass();
    }

    private void processParams(ExecutableElement methodElement) {
        for (var param : methodElement.getParameters()) {
            var type = param.asType();
            var name = param.getSimpleName().toString();
            var converter = selectConverter(type);

        }
    }

    private DependencyModel selectConverter(TypeMirror type) {
        var kind = type.getKind();
        if (kind.isPrimitive()) {
            return selectPrimitiveConverter(type);
        } else if (kind == TypeKind.DECLARED) {
            var element = (TypeElement) ((DeclaredType) type).asElement();
            if (element.getQualifiedName().contentEquals(String.class.getName())) {
                return null;
            }
            var baseClassName = StringConverter.class.getName();
            var typeClassName = element.getQualifiedName().toString();
            var typeClassSimpleName = element.getSimpleName().toString();
            var converterType = baseClassName + "<" + typeClassName + ">";
            var name = "stringConverter" + typeClassSimpleName;
            var result = new DependencyModel();
            result.setQualifiers(null); // TODO think of taking qualifiers from the method's param
            result.setName(name);
            result.setType(converterType);
            result.setCollection(false);
            result.setProvider(false);
            return result;
        } else {
            throw new IllegalArgumentException("Type " + type);
        }
    }

    private DependencyModel selectPrimitiveConverter(TypeMirror type) {
        Class<?> converter;
        switch (type.getKind()) {
            case SHORT:
                converter = StringShortConverter.class;
                break;
            case INT:
                converter = StringIntegerConverter.class;
                break;
            case DOUBLE:
                converter = StringDoubleConverter.class;
                break;
            case BOOLEAN:
                converter = StringBooleanConverter.class;
                break;
            case CHAR:
                converter = StringCharacterConverter.class;
                break;
            case FLOAT:
                converter = StringFloatConverter.class;
                break;
            case LONG:
                converter = StringLongConverter.class;
                break;
            case BYTE:
                converter = StringByteConverter.class;
                break;
            default:
                throw new IllegalArgumentException("Type " + type);
        }
        var result = new DependencyModel();
        result.setCollection(false);
        result.setProvider(false);
        result.setName(StringUtils.uncapitalize(converter.getSimpleName()));
        result.setType(converter.getName());
        result.setQualifiers(null); // TODO think of taking qualifiers from the method's param
        return result;
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

}

