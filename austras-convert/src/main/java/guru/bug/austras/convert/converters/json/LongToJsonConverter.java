package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

@Component
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
