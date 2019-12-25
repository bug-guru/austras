package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

@Default
public class ByteToJsonConverter implements JsonConverter<Byte> {
    @Override
    public void toJson(Byte value, JsonValueWriter writer) {
        writer.writeByte(value);
    }

    @Override
    public Byte fromJson(JsonValueReader reader) {
        return reader.readNullableByte();
    }

}
