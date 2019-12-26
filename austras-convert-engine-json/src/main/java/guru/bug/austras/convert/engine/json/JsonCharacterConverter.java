package guru.bug.austras.convert.engine.json;

import guru.bug.austras.convert.engine.json.reader.JsonCharacterDeserializer;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonCharacterSerializer;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

public interface JsonCharacterConverter extends JsonCharacterSerializer, JsonCharacterDeserializer {

    @Override
    void toJson(char value, JsonValueWriter writer);

    @Override
    char fromJson(JsonValueReader reader);

}
