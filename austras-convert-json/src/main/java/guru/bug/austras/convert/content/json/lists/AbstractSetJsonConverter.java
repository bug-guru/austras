package guru.bug.austras.convert.content.json.lists;

import guru.bug.austras.convert.content.json.AbstractJsonConverter;
import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings({"WeakerAccess", "RedundantSuppression"})
public abstract class AbstractSetJsonConverter<T> extends AbstractJsonConverter<Set<T>> {
    private final JsonConverter<T> elementConverter;

    public AbstractSetJsonConverter(JsonConverter<T> elementConverter) {
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
