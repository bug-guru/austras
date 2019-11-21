package guru.bug.austras.convert.converters.json.lists;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListToJsonConverter<T> implements JsonConverter<List<T>> {
    private final JsonConverter<T> elementConverter;

    public ListToJsonConverter(JsonConverter<T> elementConverter) {
        this.elementConverter = elementConverter;
    }

    @Override
    public void toJson(List<T> value, JsonValueWriter writer) {
        if (value == null) {
            writer.writeNull();
        } else {
            writer.writeArray(value, elementConverter);
        }
    }

    @Override
    public List<T> fromJson(JsonValueReader reader) {
        return reader.readArray(elementConverter)
                .map(s -> s.collect(Collectors.toList()))
                .orElse(null);
    }
}
