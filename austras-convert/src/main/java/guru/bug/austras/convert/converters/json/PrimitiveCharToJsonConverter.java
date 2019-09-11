package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonCharConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

public class PrimitiveCharToJsonConverter implements JsonCharConverter {
    @Override
    public void toJson(char value, JsonValueWriter writer) {
        writer.write(value);
    }

    @Override
    public char fromJson(JsonValueReader reader) {
        return reader.readChar();
    }

}
