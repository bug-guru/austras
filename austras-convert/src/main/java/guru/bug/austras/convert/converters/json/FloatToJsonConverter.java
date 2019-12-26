package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

@Default
public class FloatToJsonConverter implements JsonConverter<Float> {
    @Override
    public void toJson(Float value, JsonValueWriter writer) {
        writer.writeFloat(value);
    }

    @Override
    public Float fromJson(JsonValueReader reader) {
        return reader.readNullableFloat();
    }

}
