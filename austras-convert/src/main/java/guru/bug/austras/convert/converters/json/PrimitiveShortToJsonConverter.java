package guru.bug.austras.convert.converters.json;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonShortConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@Default
public class PrimitiveShortToJsonConverter implements JsonShortConverter {
    @Override
    public void toJson(short value, JsonValueWriter writer) {
        writer.writeShort(value);
    }

    @Override
    public short fromJson(JsonValueReader reader) {
        return reader.readShort();
    }

}
