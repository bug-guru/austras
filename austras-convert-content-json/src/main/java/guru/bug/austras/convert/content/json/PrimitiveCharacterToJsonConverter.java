package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.JsonCharacterConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

@ApplicationJson
public class PrimitiveCharacterJsonConverter implements JsonCharacterConverter {
    @Override
    public void toJson(char value, JsonValueWriter writer) {
        writer.writeCharacter(value);
    }

    @Override
    public char fromJson(JsonValueReader reader) {
        return reader.readChar();
    }

}
