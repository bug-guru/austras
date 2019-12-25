package guru.bug.austras.convert.converters.json;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonDoubleConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@Default
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
