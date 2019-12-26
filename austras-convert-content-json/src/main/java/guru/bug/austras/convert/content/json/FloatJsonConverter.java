package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

@ApplicationJson
public class FloatJsonConverter extends AbstractJsonConverter<Float> {
    @Override
    public void toJson(Float value, JsonValueWriter writer) {
        writer.writeFloat(value);
    }

    @Override
    public Float fromJson(JsonValueReader reader) {
        return reader.readNullableFloat();
    }

}
