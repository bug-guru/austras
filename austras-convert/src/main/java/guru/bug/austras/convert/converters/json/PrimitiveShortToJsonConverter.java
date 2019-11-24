package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonShortConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

@Component
public class PrimitiveShortToJsonConverter implements JsonShortConverter {
    @Override
    public void toJson(short value, JsonValueWriter writer) {
        writer.writeShort(value);
    }

    @Override
    public short fromJson(JsonValueReader reader) {
        return reader.readShort();
    }

}
