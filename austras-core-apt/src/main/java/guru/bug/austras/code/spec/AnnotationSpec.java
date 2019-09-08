package guru.bug.austras.code.spec;

import guru.bug.austras.code.CodePrinter;
import guru.bug.austras.code.Printable;
import guru.bug.austras.code.common.PackageName;
import guru.bug.austras.code.common.QualifiedName;
import guru.bug.austras.code.common.SimpleName;
import org.apache.commons.text.StringEscapeUtils;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationSpec implements Printable {
    private final QualifiedName name;
    private final List<ElementValuePair> pairs;

    public AnnotationSpec(QualifiedName name, List<ElementValuePair> pairs) {
        this.name = name;
        this.pairs = pairs;
    }

    public static AnnotationSpec of(String qualifiedName) {
        return new AnnotationSpec(QualifiedName.of(qualifiedName), null);
    }

    public static AnnotationSpec of(Class<? extends Annotation> annotationClass) {
        return new AnnotationSpec(QualifiedName.of(annotationClass), null);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void print(CodePrinter out) {
        out.print("@").print(name)
                .print(out.withWeakPrefix("(").weakSuffix(")").separator(", "), o -> o.print(pairs));
    }

    @Override
    public String toString() {
        return "AnnotationSpec{" +
                "name=" + name +
                ", pairs=" + pairs +
                '}';
    }

    private interface ElementValue extends Printable {

    }

    private static class ElementValuePair implements Printable {
        private final String name;
        private final ElementValue value;

        public ElementValuePair(String name, ElementValue value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public void print(CodePrinter out) {
            out.print(name).print(" = ").print(value);
        }

        @Override
        public String toString() {
            return "ElementValuePair{" +
                    "name='" + name + '\'' +
                    ", value=" + value +
                    '}';
        }
    }

    private static class ArrayElementValue implements ElementValue {
        private final List<ElementValue> elements;

        public ArrayElementValue(List<ElementValue> elements) {
            this.elements = elements;
        }

        @Override
        public void print(CodePrinter out) {
            out.print(out.withPrefix("{").suffix("}").separator(", "), o -> o.print(elements));
        }

        @Override
        public String toString() {
            return "ArrayElementValue{" +
                    "elements=" + elements +
                    '}';
        }
    }

    private static class RawElementValue implements ElementValue {
        private final String value;

        public RawElementValue(String value) {
            this.value = value;
        }

        @Override
        public void print(CodePrinter out) {
            out.print(value);
        }

        @Override
        public String toString() {
            return "RawElementValue{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }

    private static class AnnotationElementValue implements ElementValue {
        private final AnnotationSpec annotationSpec;

        public AnnotationElementValue(AnnotationSpec annotationSpec) {
            this.annotationSpec = annotationSpec;
        }

        @Override
        public void print(CodePrinter out) {
            out.print(annotationSpec);
        }

        @Override
        public String toString() {
            return "AnnotationElementValue{" +
                    "annotationSpec=" + annotationSpec +
                    '}';
        }
    }

    public static class Builder {
        private final Map<String, List<ElementValue>> pairs = new LinkedHashMap<>();
        private QualifiedName qualifiedName;


        public Builder typeName(String packageName, String simpleName) {
            this.qualifiedName = QualifiedName.of(packageName, simpleName);
            return this;
        }

        public Builder typeName(String qualifiedName) {
            this.qualifiedName = QualifiedName.of(qualifiedName);
            return this;
        }

        public Builder typeName(Class<? extends Annotation> annotationClass) {
            this.qualifiedName = QualifiedName.of(annotationClass);
            return this;
        }

        public Builder typeName(PackageName packageName, SimpleName simpleName) {
            this.qualifiedName = QualifiedName.of(packageName, simpleName);
            return this;
        }

        public Builder typeName(QualifiedName qualifiedName) {
            this.qualifiedName = qualifiedName;
            return this;
        }

        public Builder add(String value) {
            add("value", value);
            return this;
        }

        public Builder add(List<String> values) {
            add("value", values);
            return this;
        }

        public Builder add(String name, String value) {
            add(name, new RawElementValue("\"" + StringEscapeUtils.escapeJava(value) + "\""));
            return this;
        }

        public Builder add(String name, List<String> values) {
            for (var v : values) {
                add(name, v);
            }
            return this;
        }

        public Builder add(AnnotationSpec annotationSpec) {
            add("value", annotationSpec);
            return this;
        }

        public Builder add(Collection<AnnotationSpec> annotationSpecs) {
            for (var v : annotationSpecs) {
                add("value", v);
            }
            return this;
        }

        public Builder add(String name, AnnotationSpec annotationSpec) {
            add(name, new AnnotationElementValue(annotationSpec));
            return this;
        }

        private void add(String name, ElementValue value) {
            var values = pairs.computeIfAbsent(name, k -> new ArrayList<>());
            values.add(value);
        }

        public AnnotationSpec build() {
            var pairList = pairs.entrySet().stream()
                    .map(e -> {
                        var name = e.getKey();
                        var values = e.getValue();
                        if (values.size() == 1) {
                            return new ElementValuePair(name, values.get(0));
                        } else {
                            var list = new ArrayElementValue(List.copyOf(values));
                            return new ElementValuePair(name, list);
                        }
                    })
                    .collect(Collectors.toList());

            return new AnnotationSpec(qualifiedName, pairList);
        }
    }
}
