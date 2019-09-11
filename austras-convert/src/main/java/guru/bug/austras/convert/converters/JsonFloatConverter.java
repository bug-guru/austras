package guru.bug.austras.convert.converters;

import guru.bug.austras.convert.json.reader.JsonFloatDeserializer;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonFloatSerializer;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

public interface JsonFloatConverter extends JsonFloatSerializer, JsonFloatDeserializer {

    @Override
    void toJson(float value, JsonValueWriter writer);

    @Override
    float fromJson(JsonValueReader reader);

}
