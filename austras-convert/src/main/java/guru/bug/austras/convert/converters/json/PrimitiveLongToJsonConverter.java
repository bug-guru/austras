package guru.bug.austras.convert.converters.json;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonLongConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

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
