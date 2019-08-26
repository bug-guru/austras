package guru.bug.austras.code;

import org.apache.commons.text.StringEscapeUtils;

import java.util.*;
import java.util.stream.Collectors;

public class AnnotationSpec {
    private final PackageSpec packageSpec;
    private final List<ElementValuePair> pairs;

    public AnnotationSpec(PackageSpec packageSpec, List<ElementValuePair> pairs) {
        this.packageSpec = packageSpec;
        this.pairs = pairs;
    }

    public static Builder builder() {
        return new Builder();
    }

    private interface ElementValue {

    }

    private static class ElementValuePair {
        private final String name;
        private final ElementValue value;

        public ElementValuePair(String name, ElementValue value) {
            this.name = name;
            this.value = value;
        }
    }

    private static class ArrayElementValue implements ElementValue {
        private final List<ElementValue> elements;

        public ArrayElementValue(List<ElementValue> elements) {
            this.elements = elements;
        }
    }

    private static class RawElementValue implements ElementValue {
        private final String value;

        public RawElementValue(String value) {
            this.value = value;
        }
    }

    private static class AnnotationElementValue implements ElementValue {
        private final AnnotationSpec annotationSpec;

        public AnnotationElementValue(AnnotationSpec annotationSpec) {
            this.annotationSpec = annotationSpec;
        }
    }

    public static class Builder {
        private final Map<String, List<ElementValue>> pairs = new LinkedHashMap<>();
        private PackageSpec packageSpec;

        public Builder packageSpec(String packageName) {
            this.packageSpec = PackageSpec.of(packageName);
            return this;
        }

        public Builder packageSpec(PackageSpec packageSpec) {
            this.packageSpec = packageSpec;
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

            return new AnnotationSpec(packageSpec, pairList);
        }
    }
}
