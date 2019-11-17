package guru.bug.austras.convert.apt;

import guru.bug.austras.codegen.BodyBlock;
import guru.bug.austras.codegen.FromTemplate;
import guru.bug.austras.codegen.JavaGenerator;
import guru.bug.austras.codegen.TemplateException;
import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.engine.ProcessingContext;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleElementVisitor9;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@FromTemplate("JsonConverter.java.txt")
public class JsonConverterGenerator extends JavaGenerator {
    private static final PropExtractor propExtractor = new PropExtractor();
    private final ProcessingContext ctx;
    private List<Property> properties;
    private String packageName;
    private String simpleName;
    private String targetQualifiedName;
    private Property currentProperty;

    public JsonConverterGenerator(ProcessingContext ctx) throws IOException, TemplateException {
        super(ctx.processingEnv().getFiler());
        this.ctx = ctx;
    }


    public void generate(DeclaredType type) throws IOException {
        properties = collectProps(type);
        packageName = ctx.processingEnv().getElementUtils().getPackageOf(type.asElement()).getQualifiedName().toString();
        simpleName = type.asElement().getSimpleName().toString() + "ToJsonConverter";
        targetQualifiedName = ((TypeElement) type.asElement()).getQualifiedName().toString();
        generateJavaClass();
    }

    @FromTemplate("PACKAGE_NAME")
    @Override
    public String getPackageName() {
        return packageName;
    }

    @FromTemplate("SIMPLE_NAME")
    @Override
    public String getSimpleClassName() {
        return simpleName;
    }

    @FromTemplate("TARGET")
    public String getTarget() {
        return tryImport(targetQualifiedName);
    }

    @FromTemplate("PROPERTIES")
    public void processConverters(PrintWriter out, BodyBlock body) {
        for (var p : properties) {
            this.currentProperty = p;
            out.print(body.evaluateBody());
        }
    }

    @FromTemplate("PROPERTIES PARAMS")
    public void processConvertersParams(PrintWriter out, BodyBlock body) {
        boolean sep = false;
        for (var p : properties) {
            this.currentProperty = p;
            if (sep) {
                out.print(", ");
            } else {
                sep = true;
            }
            out.print(body.evaluateBody());
        }
    }

    @FromTemplate("CONVERTER_TYPE")
    public String getCurrentConverterType() {
        return tryImport(JsonConverter.class.getName()) + "<" + tryImport(currentProperty.type.toString()) + ">";
    }

    @FromTemplate("CONVERTER_NAME")
    public String getCurrentConverterName() {
        return currentProperty.name + "Converter";
    }

    @FromTemplate("PROPERTY_NAME")
    public String getCurrentPropertyName() {
        return currentProperty.name;
    }

    @FromTemplate("PROPERTY_GETTER_NAME")
    public String getCurrentPropertyGetterName() {
        return currentProperty.getter.getSimpleName().toString();
    }

    @FromTemplate("PROPERTY_SETTER_NAME")
    public String getCurrentPropertySetterName() {
        return currentProperty.setter.getSimpleName().toString();
    }

    private List<Property> collectProps(DeclaredType type) {
        var index = new HashMap<String, Property>();
        for (var e : type.asElement().getEnclosedElements()) {
            var prop = e.accept(propExtractor, ctx);
            if (prop == null) {
                continue;
            }
            index.compute(prop.name, (k, v) -> merge(prop, v, ctx));
        }
        return index.values().stream().filter(this::isValid).collect(Collectors.toList());
    }

    private boolean isValid(Property property) {
        if (property.type == null || property.name == null || property.name.isBlank()) {
            throw new IllegalArgumentException("invalid property " + property);
        }
        return property.setter != null && property.getter != null;
    }


    private Property merge(Property o1, Property o2, ProcessingContext ctx) {
        if (o2 == null && o1 == null) {
            throw new IllegalArgumentException("Properties are null");
        }
        if (o1 == null) {
            return o2;
        }
        if (o2 == null) {
            return o1;
        }
        if (!Objects.equals(o1.name, o2.name)) {
            throw new IllegalArgumentException("Different names " + o1.name + " and " + o2.name);
        }
        var e1 = o1.type.toString();
        var e2 = o2.type.toString();

        if (!e1.equals(e2)) {
            throw new IllegalArgumentException("Different types " + o1.type + " and " + o2.type);
        }
        if (o2.field != null) {
            if (o1.field != null) {
                throw new IllegalArgumentException("Field is already set");
            }
            o1.field = o2.field;
        }
        if (o2.getter != null) {
            if (o1.getter != null) {
                throw new IllegalArgumentException("Getter is already set");
            }
            o1.getter = o2.getter;
        }
        if (o2.setter != null) {
            if (o1.setter != null) {
                throw new IllegalArgumentException("Setter is already set");
            }
            o1.setter = o2.setter;
        }
        return o1;
    }


    private static class Property {
        String name;
        TypeMirror type;
        VariableElement field;
        ExecutableElement getter;
        ExecutableElement setter;

        @Override
        public String toString() {
            return "Property{" +
                    "name='" + name + '\'' +
                    ", type=" + type +
                    ", field=" + field +
                    ", getter=" + getter +
                    ", setter=" + setter +
                    '}';
        }
    }

    private static class PropExtractor extends SimpleElementVisitor9<Property, ProcessingContext> {
        @Override
        public Property visitVariable(VariableElement e, ProcessingContext ctx) {
            if (e.getKind() != ElementKind.FIELD) {
                return null;
            }
            var prop = new Property();
            prop.name = e.getSimpleName().toString();
            prop.field = e;
            prop.type = e.asType();
            return prop;
        }

        @Override
        public Property visitExecutable(ExecutableElement e, ProcessingContext ctx) {
            if (e.getKind() != ElementKind.METHOD) {
                return null;
            }
            var prop = new Property();
            var mname = e.getSimpleName().toString();
            var isVoid = e.getReturnType().getKind() == TypeKind.VOID;
            var params = e.getParameters();
            if (mname.startsWith("get") && params.isEmpty() && !isVoid) {
                prop.getter = e;
                prop.type = e.getReturnType();
            } else if (mname.startsWith("set") && params.size() == 1 && isVoid) {
                prop.setter = e;
                prop.type = params.get(0).asType();
            } else {
                return null;
            }
            prop.name = mname.substring(3, 4).toLowerCase() + mname.substring(4);
            return prop;
        }
    }
}
