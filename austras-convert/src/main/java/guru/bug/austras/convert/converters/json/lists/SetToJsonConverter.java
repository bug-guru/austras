package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings({"WeakerAccess" , "RedundantSuppression"})
public class SetToJsonConverter<T> implements JsonConverter<Set<T>> {
    private final JsonConverter<T> elementConverter;

    public SetToJsonConverter(JsonConverter<T> elementConverter) {
        this.elementConverter = elementConverter;
    }

    @Override
    public void toJson(Set<T> value, JsonValueWriter writer) {
        writer.writeValueArray(value, elementConverter);
    }

    @Override
    public Set<T> fromJson(JsonValueReader reader) {
        return reader.readOptionalArray(elementConverter)
                .map(s -> s.collect(Collectors.toSet()))
                .orElse(null);
    }
}
