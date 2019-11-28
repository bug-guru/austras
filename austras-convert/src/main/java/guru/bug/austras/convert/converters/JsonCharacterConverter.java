package guru.bug.austras.convert.converters;

import guru.bug.austras.convert.json.reader.JsonCharacterDeserializer;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonCharacterSerializer;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

public interface JsonCharacterConverter extends JsonCharacterSerializer, JsonCharacterDeserializer {

    @Override
    void toJson(char value, JsonValueWriter writer);

    @Override
    char fromJson(JsonValueReader reader);

}
