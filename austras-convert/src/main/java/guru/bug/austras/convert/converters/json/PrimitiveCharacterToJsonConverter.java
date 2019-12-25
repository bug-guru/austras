package guru.bug.austras.convert.converters.json;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonCharacterConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@Default
public class PrimitiveCharacterToJsonConverter implements JsonCharacterConverter {
    @Override
    public void toJson(char value, JsonValueWriter writer) {
        writer.writeCharacter(value);
    }

    @Override
    public char fromJson(JsonValueReader reader) {
        return reader.readChar();
    }

}
