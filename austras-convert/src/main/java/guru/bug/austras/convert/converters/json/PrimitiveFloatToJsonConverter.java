package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonFloatConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

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
