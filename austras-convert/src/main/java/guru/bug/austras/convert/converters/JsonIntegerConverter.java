package guru.bug.austras.convert.converters;

import guru.bug.austras.convert.json.reader.JsonIntDeserializer;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonIntegerSerializer;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

public interface JsonIntegerConverter extends JsonIntegerSerializer, JsonIntDeserializer {

    @Override
    void toJson(int value, JsonValueWriter writer);

    @Override
    int fromJson(JsonValueReader reader);

}
