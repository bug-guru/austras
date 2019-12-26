package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.engine.json.JsonDoubleConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

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
