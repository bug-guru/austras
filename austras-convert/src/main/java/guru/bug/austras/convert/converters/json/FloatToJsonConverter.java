package guru.bug.austras.convert.converters.json;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

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
