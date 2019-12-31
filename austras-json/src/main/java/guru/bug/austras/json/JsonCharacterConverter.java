package guru.bug.austras.json;

import guru.bug.austras.json.reader.JsonCharacterDeserializer;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonCharacterSerializer;
import guru.bug.austras.json.writer.JsonValueWriter;

public interface JsonCharacterConverter extends JsonCharacterSerializer, JsonCharacterDeserializer {

    @Override
    void toJson(char value, JsonValueWriter writer);

    @Override
    char fromJson(JsonValueReader reader);

}
