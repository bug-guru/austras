package guru.bug.austras.convert.converters;

import guru.bug.austras.convert.json.reader.JsonCharDeserializer;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonCharSerializer;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

public interface JsonCharConverter extends JsonCharSerializer, JsonCharDeserializer {

    @Override
    void toJson(char value, JsonValueWriter writer);

    @Override
    char fromJson(JsonValueReader reader);

}
