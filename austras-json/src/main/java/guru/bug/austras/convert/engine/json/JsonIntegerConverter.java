package guru.bug.austras.convert.engine.json;

import guru.bug.austras.convert.engine.json.reader.JsonIntegerDeserializer;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonIntegerSerializer;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

public interface JsonIntegerConverter extends JsonIntegerSerializer, JsonIntegerDeserializer {

    @Override
    void toJson(int value, JsonValueWriter writer);

    @Override
    int fromJson(JsonValueReader reader);

}
