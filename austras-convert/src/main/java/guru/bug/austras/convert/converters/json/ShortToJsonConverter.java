package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

@Component
public class ShortToJsonConverter implements JsonConverter<Short> {
    @Override
    public void toJson(Short value, JsonValueWriter writer) {
        writer.writeShort(value);
    }

    @Override
    public Short fromJson(JsonValueReader reader) {
        return reader.readNullableShort();
    }

}
