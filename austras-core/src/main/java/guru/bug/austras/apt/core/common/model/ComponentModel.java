/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.apt.core.common.model;

import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.util.*;
import java.util.stream.Collectors;

public class ComponentModel implements Comparable<ComponentModel> {
    private static final Serializer SERIALIZER = new Serializer();
    private final String name;
    private final String instantiable;
    private final QualifierSetModel qualifiers;
    private final Set<String> types;
    private final List<DependencyModel> dependencies;

    public ComponentModel(String name, String instantiable, QualifierSetModel qualifiers, Set<String> types, List<DependencyModel> dependencies) {
        this.name = name;
        this.instantiable = instantiable;
        this.qualifiers = qualifiers;
        this.types = Set.copyOf(types);
        this.dependencies = List.copyOf(dependencies);
    }

    public static JsonConverter<ComponentModel> serializer() {
        return SERIALIZER;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getInstantiable() {
        return instantiable;
    }

    public QualifierSetModel getQualifiers() {
        return qualifiers;
    }

    public Set<String> getTypes() {
        return types;
    }

    public List<DependencyModel> getDependencies() {
        return dependencies == null ? List.of() : dependencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentModel that = (ComponentModel) o;
        return instantiable.equals(that.instantiable);
    }

    @Override
    public int hashCode() {
        return instantiable.hashCode();
    }

    @Override
    public int compareTo(ComponentModel o) {
        return instantiable.compareTo(o.instantiable);
    }

    @Override
    public String toString() {
        return "ComponentModel{" +
                "name='" + name + '\'' +
                ", instantiable='" + instantiable + '\'' +
                ", qualifiers='" + qualifiers + '\'' +
                '}';
    }

    public static class Builder {
        private String name;
        private String instantiable;
        private QualifierSetModel qualifiers;
        private final Set<String> types = new HashSet<>();
        private final List<DependencyModel> dependencies = new ArrayList<>();

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder instantiable(String instantiable) {
            this.instantiable = instantiable;
            return this;
        }

        public Builder qualifiers(QualifierSetModel qualifiers) {
            this.qualifiers = qualifiers;
            return this;
        }

        public Builder types(Collection<String> types) {
            this.types.addAll(types);
            return this;
        }

        public Builder dependencies(List<DependencyModel> dependencies) {
            this.dependencies.addAll(dependencies);
            return this;
        }

        public ComponentModel build() {
            return new ComponentModel(name, instantiable, qualifiers, types, dependencies);
        }
    }

    private static class Serializer implements JsonConverter<ComponentModel> {

        @Override
        public void toJson(ComponentModel value, JsonValueWriter writer) {
            writer.writeObject(value, (o, w) -> {
                w.writeString("name", o.name);
                w.writeString("instantiable", o.instantiable);
                w.writeValue("qualifiers", o.qualifiers, QualifierSetModel.serializer());
                w.writeStringArray("types", o.types);
                w.writeValueArray("dependencies", o.dependencies, DependencyModel.serializer());
            });
        }

        @Override
        public ComponentModel fromJson(JsonValueReader reader) {
            return reader.readOptionalObject(ComponentModel::builder, (builder, key, in) -> {
                switch (key) {
                    case "name":
                        builder.name(in.readString());
                        break;
                    case "instantiable":
                        builder.instantiable(in.readString());
                        break;
                    case "qualifiers":
                        builder.qualifiers(in.read(QualifierSetModel.serializer()).orElseGet(QualifierSetModel::ofDefault));
                        break;
                    case "types":
                        builder.types(in.readStringArray().map(s -> s.collect(Collectors.toList())).orElseGet(List::of));
                        break;
                    case "dependencies":
                        builder.dependencies(in.readOptionalArray(DependencyModel.serializer()).map(s -> s.collect(Collectors.toList())).orElseGet(List::of));
                        break;
                    default:
                        throw new IllegalArgumentException(key);
                }
            }).map(Builder::build).orElseThrow();
        }
    }
}
