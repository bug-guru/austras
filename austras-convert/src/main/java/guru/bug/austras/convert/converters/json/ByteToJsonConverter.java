package guru.bug.austras.convert.converters.json;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

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
