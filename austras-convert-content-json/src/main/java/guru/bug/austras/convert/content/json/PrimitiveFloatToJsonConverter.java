package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.JsonFloatConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

@ApplicationJson
public class PrimitiveFloatJsonConverter implements JsonFloatConverter {
    @Override
    public void toJson(float value, JsonValueWriter writer) {
        writer.writeFloat(value);
    }

    @Override
    public float fromJson(JsonValueReader reader) {
        return reader.readFloat();
    }

}
