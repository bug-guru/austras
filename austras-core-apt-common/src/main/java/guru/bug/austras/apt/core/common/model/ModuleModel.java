package guru.bug.austras.apt.core.common.model;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ModuleModel {
    private static final Serializer SERIALIZER = new Serializer();
    private Set<ComponentModel> components;

    public ModuleModel(Collection<ComponentModel> components) {
        this.components = components == null ? Set.of() : Set.copyOf(components);
    }

    public static JsonConverter<ModuleModel> serializer() {
        return SERIALIZER;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Set<ComponentModel> getComponents() {
        return components;
    }

    private static class Serializer implements JsonConverter<ModuleModel> {
        @Override
        public void toJson(ModuleModel value, JsonValueWriter writer) {
            writer.writeObject(value,
                    (obj, out) -> out.writeValueArray("components", obj.components, ComponentModel.serializer()));
        }

        @Override
        public ModuleModel fromJson(JsonValueReader reader) {
            return reader.readOptionalObject(ModuleModel::builder, (builder, key, in) -> {
                if ("components".equals(key)) {
                    builder.components(in.readOptionalArray(ComponentModel.serializer()).map(s -> s.collect(Collectors.toList())).orElseGet(List::of));
                } else {
                    throw new IllegalArgumentException(key);
                }
            }).map(Builder::build).orElseThrow();
        }
    }

    public static class Builder {
        private final Set<ComponentModel> components = new HashSet<>();

        public Builder components(Collection<ComponentModel> components) {
            this.components.addAll(components);
            return this;
        }

        public ModuleModel build() {
            return new ModuleModel(components);
        }
    }
}
