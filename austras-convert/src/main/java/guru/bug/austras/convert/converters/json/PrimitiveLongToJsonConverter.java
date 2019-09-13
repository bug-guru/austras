package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonLongConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

@Component
public class PrimitiveLongToJsonConverter implements JsonLongConverter {
    @Override
    public void toJson(long value, JsonValueWriter writer) {
        writer.write(value);
    }

    @Override
    public long fromJson(JsonValueReader reader) {
        return reader.readLong();
    }

}
