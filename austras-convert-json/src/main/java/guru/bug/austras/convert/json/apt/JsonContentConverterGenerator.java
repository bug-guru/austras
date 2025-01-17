/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.convert.json.apt;

import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.codegen.BodyProcessor;
import guru.bug.austras.codegen.JavaFileGenerator;
import guru.bug.austras.codegen.Template;
import guru.bug.austras.convert.BooleanContentConverter;
import guru.bug.austras.json.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleElementVisitor9;
import javax.lang.model.util.TypeKindVisitor9;
import javax.tools.Diagnostic;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Template(file = "JsonContentConverter.java.txt")
public class JsonContentConverterGenerator extends JavaFileGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonContentConverterGenerator.class);
    private static final PropExtractor propExtractor = new PropExtractor();
    private final ProcessingContext ctx;
    private List<Property> properties;
    private String packageName;
    private String simpleName;
    private String targetQualifiedName;
    private Property currentProperty;
    private boolean hasMore;
    private List<ConverterInfo> converters;
    private ConverterInfo currentConverter;

    JsonContentConverterGenerator(ProcessingContext ctx) {
        this.ctx = ctx;
    }

    void generate(DeclaredType type) {
        properties = collectProps(type);
        converters = collectConverters();
        packageName = ctx.processingEnv().getElementUtils().getPackageOf(type.asElement()).getQualifiedName().toString();
        simpleName = type.asElement().getSimpleName().toString() + "JsonConverter";
        targetQualifiedName = ((TypeElement) type.asElement()).getQualifiedName().toString();
        generate(ctx.processingEnv().getFiler());
    }

    private List<ConverterInfo> collectConverters() {
        var convertersMap = new HashMap<TypeMirror, ConverterInfo>();
        for (var p : properties) {
            p.converter = convertersMap.computeIfAbsent(p.type, k -> {
                String type;
                String name;
                if (k.getKind().isPrimitive()) {
                    type = findPrimitiveConverterType(k);
                    name = "primitive" + StringUtils.capitalize(k.toString()) + "Converter";
                } else {
                    var d = (DeclaredType) k;
                    var propType = (TypeElement) d.asElement();
                    type = JsonConverter.class.getName() + "<" + k.toString() + ">";
                    name = StringUtils.uncapitalize(propType.getSimpleName().toString()) + "Converter";
                }
                return new ConverterInfo(type, name);
            });
        }
        return List.copyOf(convertersMap.values());
    }

    private String findPrimitiveConverterType(TypeMirror primitive) {
        return primitive.accept(new TypeKindVisitor9<Class<?>, Void>() {
            @Override
            public Class<?> visitPrimitiveAsBoolean(PrimitiveType t, Void aVoid) {
                return BooleanContentConverter.class;
            }

            @Override
            public Class<?> visitPrimitiveAsByte(PrimitiveType t, Void aVoid) {
                return JsonByteConverter.class;
            }

            @Override
            public Class<?> visitPrimitiveAsShort(PrimitiveType t, Void aVoid) {
                return JsonShortConverter.class;
            }

            @Override
            public Class<?> visitPrimitiveAsInt(PrimitiveType t, Void aVoid) {
                return JsonIntegerConverter.class;
            }

            @Override
            public Class<?> visitPrimitiveAsLong(PrimitiveType t, Void aVoid) {
                return JsonLongConverter.class;
            }

            @Override
            public Class<?> visitPrimitiveAsChar(PrimitiveType t, Void aVoid) {
                return JsonCharacterConverter.class;
            }

            @Override
            public Class<?> visitPrimitiveAsFloat(PrimitiveType t, Void aVoid) {
                return JsonFloatConverter.class;
            }

            @Override
            public Class<?> visitPrimitiveAsDouble(PrimitiveType t, Void aVoid) {
                return JsonDoubleConverter.class;
            }
        }, null).getName();
    }

    @Template(name = "PACKAGE_NAME")
    @Override
    public String getPackageName() {
        return packageName;
    }

    @Template(name = "SIMPLE_NAME")
    @Override
    public String getSimpleClassName() {
        return simpleName;
    }

    @Template(name = "TARGET")
    public String getTarget() {
        return tryImport(targetQualifiedName);
    }

    @Template(name = "PROPERTIES")
    public void processConvertersParams(BodyProcessor body) {
        var pi = properties.iterator();
        while (pi.hasNext()) {
            this.currentProperty = pi.next();
            this.hasMore = pi.hasNext();
            body.process();
        }
    }

    @Template(name = "CONVERTERS")
    public void processConverters(BodyProcessor body) {
        var pi = converters.iterator();
        while (pi.hasNext()) {
            this.currentConverter = pi.next();
            this.hasMore = pi.hasNext();
            body.process();
        }
    }

    @Template(name = ",")
    public String commaSeparator() {
        return hasMore ? ", " : "";
    }

    @Template(name = "CONVERTER_TYPE")
    public String getCurrentConverterType() {
        return tryImport(currentConverter.type);
    }

    @Template(name = "CONVERTER_NAME")
    public String getCurrentConverterName() {
        return currentConverter.name;
    }

    @Template(name = "PROPERTY_NAME")
    public String getCurrentPropertyName() {
        return currentProperty.name;
    }

    @Template(name = "PROPERTY_CONVERTER_NAME")
    public String getCurrentPropertyConverterName() {
        return currentProperty.converter.name;
    }

    @Template(name = "PROPERTY_GETTER_NAME")
    public String getCurrentPropertyGetterName() {
        return currentProperty.getter.getSimpleName().toString();
    }

    @Template(name = "PROPERTY_SETTER_NAME")
    public String getCurrentPropertySetterName() {
        return currentProperty.setter.getSimpleName().toString();
    }

    @Template(name = "IF_NOT_PRIMITIVE")
    public void ifNotPrimitive(BodyProcessor body) {
        if (!currentProperty.type.getKind().isPrimitive()) {
            body.process();
        }
    }

    private List<Property> collectProps(DeclaredType type) {
        var index = new HashMap<String, Property>();
        for (var e : type.asElement().getEnclosedElements()) {
            var prop = e.accept(propExtractor, ctx);
            if (prop == null) {
                continue;
            }
            try {
                index.compute(prop.name, (k, v) -> merge(prop, v));
            } catch (Exception ex) {
                LOGGER.error("Collecting props error " + type, ex);
                ctx.processingEnv().getMessager().printMessage(Diagnostic.Kind.ERROR, "Collecting props error " + type);
            }
        }
        return index.values().stream().filter(this::isValid).collect(Collectors.toList());
    }

    private boolean isValid(Property property) {
        if (property.type == null || property.name == null || property.name.isBlank()) {
            throw new IllegalArgumentException("invalid property " + property);
        }
        return property.setter != null && property.getter != null;
    }


    private Property merge(Property o1, Property o2) {
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
            throw new IllegalArgumentException("Different types " + o1 + " and " + o2);
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


    @SuppressWarnings("squid:MaximumInheritanceDepth")
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

    private static class Property {
        String name;
        TypeMirror type;
        VariableElement field;
        ExecutableElement getter;
        ExecutableElement setter;
        ConverterInfo converter;

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


    private static class ConverterInfo {
        final String type;
        final String name;

        private ConverterInfo(String type, String name) {
            this.type = type;
            this.name = name;
        }
    }
}
