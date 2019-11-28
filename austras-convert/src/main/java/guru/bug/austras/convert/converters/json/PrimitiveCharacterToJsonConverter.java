package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonCharacterConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

@Component
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
