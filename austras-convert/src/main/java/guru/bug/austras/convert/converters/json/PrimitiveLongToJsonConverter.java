package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.engine.json.JsonLongConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

@Default
public class PrimitiveLongToJsonConverter implements JsonLongConverter {
    @Override
    public void toJson(long value, JsonValueWriter writer) {
        writer.writeLong(value);
    }

    @Override
    public long fromJson(JsonValueReader reader) {
        return reader.readLong();
    }

}
