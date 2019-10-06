package guru.bug.austras.convert.apt;

import guru.bug.austras.codegen.CompilationUnit;
import guru.bug.austras.codegen.Printable;
import guru.bug.austras.codegen.common.CodeBlock;
import guru.bug.austras.codegen.common.CodeLine;
import guru.bug.austras.codegen.common.QualifiedName;
import guru.bug.austras.codegen.decl.ClassMemberDecl;
import guru.bug.austras.codegen.decl.MethodParamDecl;
import guru.bug.austras.codegen.decl.PackageDecl;
import guru.bug.austras.codegen.decl.TypeDecl;
import guru.bug.austras.codegen.spec.AnnotationSpec;
import guru.bug.austras.codegen.spec.TypeArg;
import guru.bug.austras.codegen.spec.TypeSpec;
import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;
import guru.bug.austras.engine.ProcessingContext;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleElementVisitor9;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonConverterGenerator {
    private static final PropExtractor propExtractor = new PropExtractor();
    private final ProcessingContext ctx;

    public JsonConverterGenerator(ProcessingContext ctx) {
        this.ctx = ctx;
    }


    public void generate(DeclaredType type) throws IOException {
        List<Property> props = collectProps(type);
        var pkg = ctx.processingEnv().getElementUtils().getPackageOf(type.asElement()).getQualifiedName().toString();
        var unit = CompilationUnit.builder()
                .packageDecl(PackageDecl.of(pkg))
                .addTypeDecl(TypeDecl.classBuilder()
                        .simpleName(type.asElement().getSimpleName().toString() + "ToJsonConverter")
                        .publicMod()
                        .addAnnotation(AnnotationSpec.of(Component.class))
                        .addSuperinterface(TypeSpec.builder()
                                .name(QualifiedName.of(JsonConverter.class))
                                .addTypeArg(TypeArg.ofType(type.toString()))
                                .build())
                        .addMembers(fields(props))
                        .addMember(ClassMemberDecl.constructorBuilder()
                                .publicMod()
                                .addParams(constructorParams(props))
                                .body(constructorBody(props))
                                .build())
                        .addMember(toJsonMethod(type, props))
                        .addMember(fromJsonMethod(type, props))
                        .build())
                .build();

        ctx.fileManager().createFile(unit);
    }

    private ClassMemberDecl toJsonMethod(DeclaredType type, List<Property> props) {
        return ClassMemberDecl.methodBuilder("toJson", TypeSpec.voidType())
                .addAnnotation(AnnotationSpec.of(Override.class))
                .publicMod()
                .addParam(MethodParamDecl.builder()
                        .name("value")
                        .type(type.toString())
                        .build())
                .addParam(MethodParamDecl.builder()
                        .name("writer")
                        .type(TypeSpec.of(JsonValueWriter.class))
                        .build())
                .body(toJsonMethodBody(props))
                .build();
    }

    private CodeBlock toJsonMethodBody(List<Property> props) {
        return CodeBlock.builder()
                .addLine(CodeLine.builder()
                        .add(out -> out.print(out.withIndent(4)
                                        .separator("\n")
                                        .prefix("writer.writeObject(value, (v, out) -> {\n")
                                        .suffix("\n});"),
                                o -> o.print(props.stream()
                                        .map(p -> (Printable) printer -> printer.print("out.write(")
                                                .printLiteral(p.name)
                                                .print(", v.")
                                                .print(p.getter.getSimpleName().toString())
                                                .print("(), ")
                                                .print(p.name + "Converter);"))
                                        .collect(Collectors.toList()))))
                        .build())
                .build();
    }

    private ClassMemberDecl fromJsonMethod(DeclaredType type, List<Property> props) {
        return ClassMemberDecl.methodBuilder("fromJson", TypeSpec.of(type.toString()))
                .addAnnotation(AnnotationSpec.of(Override.class))
                .publicMod()
                .addParam(MethodParamDecl.builder()
                        .name("reader")
                        .type(TypeSpec.of(JsonValueReader.class))
                        .build())
                .body(fromJsonMethodBody(type, props))
                .build();
    }

    private CodeBlock fromJsonMethodBody(DeclaredType type, List<Property> props) {
        return CodeBlock.builder()
                .addLine(CodeLine.builder()
                        .add(out -> out.print(out.withIndent(4)
                                        .separator("\n")
                                        .prefix(prefixPrinter -> {
                                            prefixPrinter.printReturn()
                                                    .print("reader.readObject()")
                                                    .print(QualifiedName.of(type.toString()))
                                                    .print("::")
                                                    .printNew()
                                                    .print(", (v, k, r) -> {\n");
                                            prefixPrinter.print(x -> x.withIndent(4), xo -> xo.print())
                                        })
                                        .suffix("\n});"),
                                o -> o.print(props.stream()
                                        .map(p -> (Printable) printer -> printer.print("out.write(")
                                                .printLiteral(p.name)
                                                .print(", v.")
                                                .print(p.getter.getSimpleName().toString())
                                                .print("(), ")
                                                .print(p.name + "Converter);"))
                                        .collect(Collectors.toList()))))
                        .build())
                .build();
    }

    private Collection<ClassMemberDecl> fields(List<Property> props) {
        return props.stream()
                .map(p -> ClassMemberDecl.fieldBulder(TypeSpec.of(JsonConverter.class, TypeArg.ofType(p.type.toString())), p.name + "Converter")
                        .privateMod()
                        .build())
                .collect(Collectors.toList());
    }

    private CodeBlock constructorBody(List<Property> props) {
        return CodeBlock.builder()
                .addLines(props.stream()
                        .map(p -> CodeLine.builder()
                                .print(o -> {
                                    o.printThis();
                                    o.print(".");
                                    o.print(p.name + "Converter");
                                    o.print(" = ");
                                    o.print(p.name + "Converter");
                                    o.print(";");
                                })
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private Collection<MethodParamDecl> constructorParams(List<Property> props) {

        return props.stream()
                .map(p -> {
                    try {
                        return MethodParamDecl.builder()
                                .type(TypeSpec.of(JsonConverter.class, TypeArg.ofType(p.type.toString())))
                                .name(p.name + "Converter")
                                .build();
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Property " + p, e);
                    }
                })
                .collect(Collectors.toList());
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
