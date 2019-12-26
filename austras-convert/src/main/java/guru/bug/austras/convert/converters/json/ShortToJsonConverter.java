package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

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
