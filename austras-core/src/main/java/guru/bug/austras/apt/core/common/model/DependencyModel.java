/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.apt.core.common.model;

import guru.bug.austras.common.model.WrappingType;
import guru.bug.austras.core.Selector;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import javax.lang.model.type.TypeMirror;
import java.util.Collection;
import java.util.Objects;

public class DependencyModel {
    private static final Serializer SERIALIZER = new Serializer();
    private final String type;
    private final TypeMirror mirror;
    private final QualifierSetModel qualifiers;
    private final WrappingType wrapping;

    private DependencyModel(String type, TypeMirror mirror, QualifierSetModel qualifiers, WrappingType wrapping) {
        this.type = type;
        this.mirror = mirror;
        this.qualifiers = qualifiers;
        this.wrapping = wrapping;
    }

    public static JsonConverter<DependencyModel> serializer() {
        return SERIALIZER;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getType() {
        return type;
    }

    public TypeMirror getMirror() {
        return mirror;
    }

    public QualifierSetModel getQualifiers() {
        return qualifiers;
    }

    public WrappingType getWrapping() {
        return wrapping;
    }

    public ComponentKey asComponentKey() {
        return ComponentKey.of(type, qualifiers);
    }

    public ComponentRef asComponentRef() {
        return ComponentRef.of(mirror, qualifiers);
    }

    public String asTypeDeclaration() {
        switch (wrapping) {
            case NONE:
                return type;
            case COLLECTION:
                return String.format("%s<? extends %s>", Collection.class.getName(), type);
            case SELECTOR:
                return String.format("%s<? extends %s>", Selector.class.getName(), type);
            default:
                throw new IllegalArgumentException("wrapping " + wrapping);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependencyModel that = (DependencyModel) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(qualifiers, that.qualifiers) &&
                wrapping == that.wrapping;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, qualifiers, wrapping);
    }

    @Override
    public String toString() {
        return "DependencyModel{" +
                "type='" + type + '\'' +
                ", qualifiers=" + qualifiers +
                ", wrapping=" + wrapping +
                '}';
    }

    private static class Serializer implements JsonConverter<DependencyModel> {

        @Override
        public void toJson(DependencyModel value, JsonValueWriter writer) {
            writer.writeObject(value, (obj, out) -> {
                out.writeString("type", obj.type);
                out.writeString("wrapping", obj.wrapping.name());
                out.writeValue("qualifiers", obj.qualifiers, QualifierSetModel.serializer());
            });
        }

        @Override
        public DependencyModel fromJson(JsonValueReader reader) {
            return reader.readOptionalObject(DependencyModel::builder, (builder, key, in) -> {
                switch (key) {
                    case "type":
                        builder.type(in.readString());
                        break;
                    case "wrapping":
                        builder.wrapping(WrappingType.valueOf(in.readString()));
                        break;
                    case "qualifiers":
                        builder.qualifiers(in.read(QualifierSetModel.serializer()).orElseGet(QualifierSetModel::ofDefault));
                        break;
                    default:
                        throw new IllegalArgumentException(key);
                }
            }).map(Builder::build).orElseThrow();
        }
    }

    public static class Builder {
        private String type;
        private TypeMirror mirror;
        private QualifierSetModel qualifiers;
        private WrappingType wrapping;

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder mirror(TypeMirror mirror) {
            this.mirror = mirror;
            return this;
        }

        public Builder qualifiers(QualifierSetModel qualifiers) {
            this.qualifiers = qualifiers;
            return this;
        }

        public Builder wrapping(WrappingType wrapping) {
            this.wrapping = wrapping;
            return this;
        }

        public DependencyModel build() {
            return new DependencyModel(type, mirror, qualifiers, wrapping);
        }
    }
}
