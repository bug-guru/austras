/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.apt.core.common.model;

import guru.bug.austras.core.qualifiers.Any;
import guru.bug.austras.core.qualifiers.Broadcast;
import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.core.qualifiers.Qualifier;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QualifierModel implements Comparable<QualifierModel> {
    public static final QualifierModel DEFAULT = new QualifierModel(Default.QUALIFIER_NAME);
    public static final QualifierModel ANY = new QualifierModel(Any.QUALIFIER_NAME);
    public static final QualifierModel BROADCAST = new QualifierModel(Broadcast.QUALIFIER_NAME);
    private static final Serializer SERIALIZER = new Serializer();
    private final String name;
    private final Map<String, QualifierPropertyModel> properties;

    private QualifierModel(String name, Stream<QualifierPropertyModel> properties) {
        this.name = name;
        this.properties = new TreeMap<>(properties.collect(Collectors.toMap(QualifierPropertyModel::getName, Function.identity())));
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

    public static QualifierModel of(String name, QualifierPropertyModel... properties) {
        return new QualifierModel(name, Stream.of(properties));
    }

    public String getName() {
        return name;
    }

    public Collection<QualifierPropertyModel> getProperties() {
        return properties.values();
    }

    @Override
    public String toString() {
        var result = new StringBuilder(64);
        result.append("@")
                .append(Qualifier.class.getSimpleName())
                .append("(name = \"")
                .append(name)
                .append("\"");
        if (!properties.isEmpty()) {
            result.append(", properties = ");
            if (properties.size() > 1) {
                result.append("{");
            }
            result.append(properties.values().stream()
                    .map(QualifierPropertyModel::toString)
                    .collect(Collectors.joining(", "))
            );
            if (properties.size() > 1) {
                result.append("}");
            }
        }
        result.append(")");
        return result.toString();
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

    @Override
    public int compareTo(QualifierModel o) {
        var result = this.name.compareTo(o.name);
        if (result != 0) {
            return result;
        }
        var thisIterator = this.properties.values().iterator();
        var otherIterator = o.properties.values().iterator();
        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            result = thisIterator.next().compareTo(otherIterator.next());
            if (result != 0) {
                return result;
            }
        }
        if (!thisIterator.hasNext() && !otherIterator.hasNext()) {
            return 0;
        }
        if (thisIterator.hasNext()) {
            return 1;
        } else {
            return -1;
        }
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
