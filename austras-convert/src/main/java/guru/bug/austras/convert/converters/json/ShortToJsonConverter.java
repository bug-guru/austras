package guru.bug.austras.convert.converters.json;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@Default
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
