package guru.bug.austras.convert.engine.json;

import guru.bug.austras.convert.engine.json.reader.JsonFloatDeserializer;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonFloatSerializer;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

public interface JsonFloatConverter extends JsonFloatSerializer, JsonFloatDeserializer {

    @Override
    void toJson(float value, JsonValueWriter writer);

    @Override
    float fromJson(JsonValueReader reader);

}
