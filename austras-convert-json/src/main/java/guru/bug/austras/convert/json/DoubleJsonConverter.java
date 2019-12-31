package guru.bug.austras.convert.json;


import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@ApplicationJson
public class DoubleJsonConverter extends AbstractJsonConverter<Double> {
    @Override
    public void toJson(Double value, JsonValueWriter writer) {
        writer.writeDouble(value);
    }

    @Override
    public Double fromJson(JsonValueReader reader) {
        return reader.readNullableDouble();
    }

}
