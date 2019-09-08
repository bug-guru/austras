package guru.bug.austras.apt.model;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class QualifierModel {
    private Map<String, Map<String, String>> qualifiers = new HashMap<>();

    private Map<String, String> computeIfAbsent(String qualifierName) {
        Objects.requireNonNull(qualifierName, "qualifierName must be not null");
        return this.qualifiers.computeIfAbsent(qualifierName, k -> new HashMap<>());
    }

    public void setProperty(String qualifierName) {
        computeIfAbsent(qualifierName);
    }

    public void setProperty(String qualifierName, String propertyName, String value) {
        Objects.requireNonNull(propertyName, "propertyName must be not null");
        Objects.requireNonNull(value, "value must be not null");
        computeIfAbsent(qualifierName).put(propertyName, value);
    }

    public boolean contains(String qualifierName) {
        return qualifiers.containsKey(qualifierName);
    }

    public boolean contains(String qualifierName, String propertyName) {
        var props = qualifiers.get(qualifierName);
        return props != null && props.containsKey(propertyName);
    }

    public boolean contains(String qualifierName, String propertyName, String value) {
        var props = qualifiers.get(qualifierName);
        if (props == null) {
            return false;
        }
        var actualValue = props.get(propertyName);
        return Objects.equals(actualValue, value);
    }

    public boolean containsAll(QualifierModel other) {
        for (var otherQ : other.qualifiers.entrySet()) {
            var otherProps = otherQ.getValue();
            var thisProps = qualifiers.get(otherQ.getKey());
            if (!Objects.equals(otherProps, thisProps)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        return qualifiers.isEmpty();
    }

    public void forEach(BiConsumer<String, List<Pair<String, String>>> visitor) {
        qualifiers.forEach((k, v) -> {
            var pairs = v.entrySet().stream()
                    .map(e -> Pair.of(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());
            visitor.accept(k, pairs);
        });
    }

    @Override
    public String toString() {
        return "QualifierModel{" +
                "qualifiers=" + qualifiers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QualifierModel that = (QualifierModel) o;
        return qualifiers.equals(that.qualifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qualifiers);
    }

}
