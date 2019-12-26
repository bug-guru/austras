package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

@ApplicationJson
public class ByteJsonConverter extends AbstractJsonConverter<Byte> {
    @Override
    public void toJson(Byte value, JsonValueWriter writer) {
        writer.writeByte(value);
    }

    @Override
    public Byte fromJson(JsonValueReader reader) {
        return reader.readNullableByte();
    }

}
