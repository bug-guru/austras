package guru.bug.austras.convert.converters.json;


import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

@Default
public class DoubleToJsonConverter implements JsonConverter<Double> {
    @Override
    public void toJson(Double value, JsonValueWriter writer) {
        writer.writeDouble(value);
    }

    @Override
    public Double fromJson(JsonValueReader reader) {
        return reader.readNullableDouble();
    }

}
