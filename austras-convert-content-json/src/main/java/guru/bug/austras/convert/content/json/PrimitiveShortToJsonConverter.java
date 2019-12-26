package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.JsonShortConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

@ApplicationJson
public class PrimitiveShortJsonConverter implements JsonShortConverter {
    @Override
    public void toJson(short value, JsonValueWriter writer) {
        writer.writeShort(value);
    }

    @Override
    public short fromJson(JsonValueReader reader) {
        return reader.readShort();
    }

}
