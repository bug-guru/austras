package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

import java.util.Set;
import java.util.stream.Collectors;

public class SetToJsonConverter<T> implements JsonConverter<Set<T>> {
    private final JsonConverter<T> elementConverter;

    public SetToJsonConverter(JsonConverter<T> elementConverter) {
        this.elementConverter = elementConverter;
    }

    @Override
    public void toJson(Set<T> value, JsonValueWriter writer) {
        if (value == null) {
            writer.writeNull();
        } else {
            writer.writeArray(value, elementConverter);
        }
    }

    @Override
    public Set<T> fromJson(JsonValueReader reader) {
        return reader.readArray(elementConverter)
                .map(s -> s.collect(Collectors.toSet()))
                .orElse(null);
    }
}
