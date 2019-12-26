package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public class ListJsonConverter<T> extends AbstractJsonConverter<List<T>> {
    private final JsonConverter<T> elementConverter;

    public ListJsonConverter(JsonConverter<T> elementConverter) {
        this.elementConverter = elementConverter;
    }

    @Override
    public void toJson(List<T> value, JsonValueWriter writer) {
        writer.writeValueArray(value, elementConverter);
    }

    @Override
    public List<T> fromJson(JsonValueReader reader) {
        return reader.readOptionalArray(elementConverter)
                .map(s -> s.collect(Collectors.toList()))
                .orElse(null);
    }
}
