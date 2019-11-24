package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonDoubleConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

@Component
public class PrimitiveDoubleToJsonConverter implements JsonDoubleConverter {
    @Override
    public void toJson(double value, JsonValueWriter writer) {
        writer.writeDouble(value);
    }

    @Override
    public double fromJson(JsonValueReader reader) {
        return reader.readDouble();
    }

}
