package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonByteConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

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
