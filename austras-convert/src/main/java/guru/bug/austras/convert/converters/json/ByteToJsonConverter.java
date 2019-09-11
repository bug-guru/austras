package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

public class ByteToJsonConverter implements JsonConverter<Byte> {
    @Override
    public void toJson(Byte value, JsonValueWriter writer) {
        writer.write(value);
    }

    @Override
    public Byte fromJson(JsonValueReader reader) {
        return reader.readNullableByte();
    }

}
