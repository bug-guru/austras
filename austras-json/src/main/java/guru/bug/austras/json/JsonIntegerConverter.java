package guru.bug.austras.json;

import guru.bug.austras.json.reader.JsonIntegerDeserializer;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonIntegerSerializer;
import guru.bug.austras.json.writer.JsonValueWriter;

public interface JsonIntegerConverter extends JsonIntegerSerializer, JsonIntegerDeserializer {

    @Override
    void toJson(int value, JsonValueWriter writer);

    @Override
    int fromJson(JsonValueReader reader);

}
