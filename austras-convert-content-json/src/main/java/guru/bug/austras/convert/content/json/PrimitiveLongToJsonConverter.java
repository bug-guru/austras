package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.JsonLongConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

@ApplicationJson
public class PrimitiveLongJsonConverter implements JsonLongConverter {
    @Override
    public void toJson(long value, JsonValueWriter writer) {
        writer.writeLong(value);
    }

    @Override
    public long fromJson(JsonValueReader reader) {
        return reader.readLong();
    }

}
