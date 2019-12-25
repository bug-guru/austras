package guru.bug.austras.convert.converters.json;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonByteConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@Default
public class PrimitiveByteToJsonConverter implements JsonByteConverter {
    @Override
    public void toJson(byte value, JsonValueWriter writer) {
        writer.writeByte(value);
    }

    @Override
    public byte fromJson(JsonValueReader reader) {
        return reader.readByte();
    }

}
