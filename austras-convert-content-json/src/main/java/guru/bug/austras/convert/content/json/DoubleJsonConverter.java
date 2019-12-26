package guru.bug.austras.convert.content.json;


import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

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
