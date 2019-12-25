package guru.bug.austras.convert.converters.json;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@Default
public class LongToJsonConverter implements JsonConverter<Long> {
    @Override
    public void toJson(Long value, JsonValueWriter writer) {
        writer.writeLong(value);
    }

    @Override
    public Long fromJson(JsonValueReader reader) {
        return reader.readNullableLong();
    }

}
