package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.engine.json.JsonShortConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

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
