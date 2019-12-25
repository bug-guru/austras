package guru.bug.austras.apt.core.common.model;

import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

import java.util.Objects;

public class DependencyModel {
    private static final Serializer SERIALIZER = new Serializer();
    private final String type;
    private final QualifierSetModel qualifiers;
    private final WrappingType wrapping;

    public DependencyModel(String type, QualifierSetModel qualifiers, WrappingType wrapping) {
        this.type = type;
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

    public QualifierSetModel getQualifiers() {
        return qualifiers;
    }

    public WrappingType getWrapping() {
        return wrapping;
    }

    public ComponentKey asComponentKey() {
        return ComponentKey.of(type, qualifiers);
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
        private QualifierSetModel qualifiers;
        private WrappingType wrapping;

        public Builder type(String type) {
            this.type = type;
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
            return new DependencyModel(type, qualifiers, wrapping);
        }
    }
}
