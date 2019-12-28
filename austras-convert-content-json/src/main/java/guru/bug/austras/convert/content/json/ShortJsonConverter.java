package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

@ApplicationJson
public class ShortJsonConverter extends AbstractJsonConverter<Short> {
    @Override
    public void toJson(Short value, JsonValueWriter writer) {
        writer.writeShort(value);
    }

    @Override
    public Short fromJson(JsonValueReader reader) {
        return reader.readNullableShort();
    }

}