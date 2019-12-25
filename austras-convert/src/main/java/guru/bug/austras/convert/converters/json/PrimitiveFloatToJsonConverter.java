package guru.bug.austras.convert.converters.json;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonFloatConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@Default
public class PrimitiveFloatToJsonConverter implements JsonFloatConverter {
    @Override
    public void toJson(float value, JsonValueWriter writer) {
        writer.writeFloat(value);
    }

    @Override
    public float fromJson(JsonValueReader reader) {
        return reader.readFloat();
    }

}
