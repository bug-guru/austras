/*
 * Copyright (c) 2019 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.apt.core.common.model;

import guru.bug.austras.core.qualifiers.QualifierProperty;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.util.Objects;

public class QualifierPropertyModel implements Comparable<QualifierPropertyModel> {
    private static final Serializer SERIALIZER = new Serializer();
    private final String name;
    private final String value;

    public QualifierPropertyModel(String name, String value) {
        this.name = Objects.requireNonNull(name);
        this.value = Objects.requireNonNull(value);
    }

    public static QualifierPropertyModel of(QualifierProperty property) {
        return new QualifierPropertyModel(property.name(), property.value());
    }

    public static QualifierPropertyModel of(String name, String value) {
        return new QualifierPropertyModel(name, value);
    }

    public static JsonConverter<QualifierPropertyModel> serializer() {
        return SERIALIZER;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("@%s(name = \"%s\", value = \"%s\")",
                QualifierProperty.class.getSimpleName(),
                name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QualifierPropertyModel that = (QualifierPropertyModel) o;
        return name.equals(that.name) &&
                value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public int compareTo(QualifierPropertyModel o) {
        var result = this.name.compareTo(o.name);
        if (result != 0) {
            return result;
        }
        result = this.value.compareTo(o.value);
        return result;
    }

    public static class Builder {
        String name;
        String value;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public QualifierPropertyModel build() {
            return new QualifierPropertyModel(name, value);
        }
    }

    private static class Serializer implements JsonConverter<QualifierPropertyModel> {
        @Override
        public void toJson(QualifierPropertyModel value, JsonValueWriter writer) {
            writer.writeObject(value, (o, w) -> {
                w.writeString("name", o.name);
                w.writeString("value", o.value);
            });
        }

        @Override
        public QualifierPropertyModel fromJson(JsonValueReader reader) {
            return reader.readNullableObject(QualifierPropertyModel::builder, (builder, key, in) -> {
                switch (key) {
                    case "name":
                        builder.name(in.readString());
                        break;
                    case "value":
                        builder.value(in.readString());
                        break;
                    default:
                        throw new IllegalArgumentException(key);
                }
            }).build();
        }
    }

}
