package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonIntConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

@Component
public class PrimitiveIntToJsonConverter implements JsonIntConverter {
    @Override
    public void toJson(int value, JsonValueWriter writer) {
        writer.writeInteger(value);
    }

    @Override
    public int fromJson(JsonValueReader reader) {
        return reader.readByte();
    }

}
