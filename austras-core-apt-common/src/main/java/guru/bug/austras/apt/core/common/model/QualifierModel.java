package guru.bug.austras.apt.core.common.model;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Qualifier;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QualifierModel {
    private static final Serializer SERIALIZER = new Serializer();
    static final QualifierModel DEFAULT = new QualifierModel("austras.default");
    static final QualifierModel ANY = new QualifierModel("austras.any");
    private final String name;
    private final Map<String, QualifierPropertyModel> properties;

    private QualifierModel(String name, Stream<QualifierPropertyModel> properties) {
        this.name = name;
        this.properties = Map.copyOf(properties.collect(Collectors.toMap(QualifierPropertyModel::getName, Function.identity())));
    }

    public QualifierModel(String name, QualifierPropertyModel... properties) {
        this(name, Stream.of(properties));
    }

    public static JsonConverter<QualifierModel> serializer() {
        return SERIALIZER;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static QualifierModel of(Qualifier qualifier) {
        return new QualifierModel(
                qualifier.name(),
                Stream.of(qualifier.properties()).map(QualifierPropertyModel::of)
        );
    }

    public String getName() {
        return name;
    }

    public Collection<QualifierPropertyModel> getProperties() {
        return properties.values();
    }

    @Override
    public String toString() {
        return "QualifierModel{" +
                "name='" + name + '\'' +
                ", properties=" + properties +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QualifierModel that = (QualifierModel) o;
        return name.equals(that.name) &&
                properties.equals(that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, properties);
    }

    public static class Builder {
        private String name;
        private Set<QualifierPropertyModel> props = new HashSet<>();

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder addProperty(QualifierPropertyModel property) {
            this.props.add(property);
            return this;
        }

        public Builder addProperties(Collection<QualifierPropertyModel> properties) {
            this.props.addAll(properties);
            return this;
        }

        public QualifierModel build() {
            return new QualifierModel(name, props.stream());
        }
    }

    private static class Serializer implements JsonConverter<QualifierModel> {

        @Override
        public void toJson(QualifierModel value, JsonValueWriter writer) {
            writer.writeObject(value, (qm, qmOut) -> {
                qmOut.writeString("name", qm.name);
                qmOut.writeValueArray("properties", qm.properties.values(), QualifierPropertyModel.serializer());
            });
        }

        @Override
        public QualifierModel fromJson(JsonValueReader reader) {
            return reader.readNullableObject(QualifierModel::builder, (builder, key, in) -> {
                switch (key) {
                    case "name":
                        builder.name(in.readString());
                        break;
                    case "properties":
                        builder.addProperties(in.readOptionalArray(QualifierPropertyModel.serializer())
                                .map(a -> a.collect(Collectors.toList()))
                                .orElse(List.of()));
                        break;
                    default:
                        throw new IllegalArgumentException(key);
                }
            }).build();
        }
    }

}
