package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.JsonIntegerConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

@ApplicationJson
public class PrimitiveIntegerJsonConverter implements JsonIntegerConverter {
    @Override
    public void toJson(int value, JsonValueWriter writer) {
        writer.writeInteger(value);
    }

    @Override
    public int fromJson(JsonValueReader reader) {
        return reader.readByte();
    }

}
