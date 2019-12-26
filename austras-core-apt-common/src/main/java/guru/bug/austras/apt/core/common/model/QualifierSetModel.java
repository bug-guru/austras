package guru.bug.austras.apt.core.common.model;

import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.core.qualifiers.Qualifier;
import guru.bug.austras.core.qualifiers.Qualifiers;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QualifierSetModel {
    private static final Serializer SERIALIZER = new Serializer();
    private static final QualifierSetModel DEFAULT = new QualifierSetModel(QualifierModel.DEFAULT);
    private static final QualifierSetModel ANY = new QualifierSetModel(QualifierModel.ANY);
    private final Map<String, QualifierModel> qualifiers;

    private QualifierSetModel(Stream<QualifierModel> qualifiers) {
        var map = qualifiers.collect(Collectors.toMap(QualifierModel::getName, Function.identity()));
        if (map.isEmpty()) {
            map = Map.of(Default.QUALIFIER_NAME, QualifierModel.DEFAULT);
        }
        this.qualifiers = Map.copyOf(map);
    }

    public QualifierSetModel(QualifierModel... qualifiers) {
        this(Stream.of(qualifiers));
    }

    public QualifierSetModel(Collection<QualifierModel> qualifiers) {
        this(qualifiers.stream());
    }

    public static JsonConverter<QualifierSetModel> serializer() {
        return SERIALIZER;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static QualifierSetModel ofDefault() {
        return QualifierSetModel.DEFAULT;
    }

    public static QualifierSetModel ofAny() {
        return QualifierSetModel.ANY;
    }

    public static QualifierSetModel of(Qualifiers qualifiers) {
        var q = Objects.requireNonNull(qualifiers, "qualifiers").value();
        return new QualifierSetModel(Stream.of(q).map(QualifierModel::of));
    }

    public static QualifierSetModel of(Collection<Qualifier> qualifiers) {
        var q = Objects.requireNonNull(qualifiers, "qualifiers");
        return new QualifierSetModel(q.stream().map(QualifierModel::of));
    }

    public QualifierSetModel minus(QualifierModel qualifier) {
        return new QualifierSetModel(this.qualifiers.values().stream()
                .filter(qm -> !qm.equals(qualifier)));
    }

    public Collection<QualifierModel> getAll() {
        return qualifiers.values();
    }

    public boolean isEmpty() {
        return qualifiers.isEmpty();
    }

    @Override
    public String toString() {
        return "QualifierSetModel{" +
                "qualifiers=" + qualifiers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QualifierSetModel that = (QualifierSetModel) o;
        return qualifiers.equals(that.qualifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qualifiers);
    }

    private static class Serializer implements JsonConverter<QualifierSetModel> {

        @Override
        public void toJson(QualifierSetModel value, JsonValueWriter writer) {
            writer.writeValueArray(value.qualifiers.values(), QualifierModel.serializer());
        }

        @Override
        public QualifierSetModel fromJson(JsonValueReader reader) {
            var qualifiers = reader.readOptionalArray(QualifierModel.serializer()).map(s -> s.collect(Collectors.toList())).orElse(List.of());
            return new QualifierSetModel(qualifiers);
        }
    }

    public static class Builder {
        private List<QualifierModel> qualifiers = new ArrayList<>();

        public Builder addQualifier(QualifierModel qualifier) {
            this.qualifiers.add(qualifier);
            return this;
        }

        public Builder addQualifiers(QualifierModel... qualifiers) {
            this.qualifiers.addAll(Arrays.asList(qualifiers));
            return this;
        }

        public Builder addQualifiers(Collection<QualifierModel> qualifiers) {
            this.qualifiers.addAll(qualifiers);
            return this;
        }

        public QualifierSetModel build() {
            return new QualifierSetModel(qualifiers);
        }
    }
}
