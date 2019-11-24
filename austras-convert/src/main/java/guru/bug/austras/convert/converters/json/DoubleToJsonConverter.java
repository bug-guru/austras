package guru.bug.austras.convert.converters.json;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

@Component
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
