package guru.bug.austras.code;

import org.apache.commons.text.StringEscapeUtils;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationSpec implements Writable {
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
    public void write(CodeWriter out) {
        out.write("@");
        out.write(name);
        if (pairs != null && !pairs.isEmpty()) {
            out.write("(");
            for (var p : pairs) {
                out.write(p);
            }
            out.write(")");
        }
        out.write("\n");
    }

    private interface ElementValue extends Writable {

    }

    private static class ElementValuePair implements Writable {
        private final String name;
        private final ElementValue value;

        public ElementValuePair(String name, ElementValue value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public void write(CodeWriter out) {
            out.print(name);
            out.print(" = ");
            value.write(out);
        }
    }

    private static class ArrayElementValue implements ElementValue {
        private final List<ElementValue> elements;

        public ArrayElementValue(List<ElementValue> elements) {
            this.elements = elements;
        }

        @Override
        public void write(CodeWriter out) {
            out.print("{");
            var delim = false;
            for (var e : elements) {
                if (delim) {
                    out.write(", ");
                } else {
                    delim = true;
                }
                e.write(out);
            }
            out.print("}");
        }
    }

    private static class RawElementValue implements ElementValue {
        private final String value;

        public RawElementValue(String value) {
            this.value = value;
        }

        @Override
        public void write(CodeWriter out) {
            out.print(value);
        }
    }

    private static class AnnotationElementValue implements ElementValue {
        private final AnnotationSpec annotationSpec;

        public AnnotationElementValue(AnnotationSpec annotationSpec) {
            this.annotationSpec = annotationSpec;
        }

        @Override
        public void write(CodeWriter out) {
            annotationSpec.write(out);
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
