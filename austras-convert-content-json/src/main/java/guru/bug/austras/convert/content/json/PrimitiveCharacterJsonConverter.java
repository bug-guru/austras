package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.CharacterContentConverter;
import guru.bug.austras.convert.engine.json.JsonCharacterConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@ApplicationJson
public class PrimitiveCharacterJsonConverter implements CharacterContentConverter, JsonCharacterConverter {
    @Override
    public void toJson(char value, JsonValueWriter writer) {
        writer.writeCharacter(value);
    }

    @Override
    public char fromJson(JsonValueReader reader) {
        return reader.readChar();
    }

    @Override
    public char fromString(String value) {
        return read(new StringReader(value));
    }

    @Override
    public String toString(char value) {
        var result = new StringWriter(10);
        write(value, result);
        return result.toString();
    }

    @Override
    public char read(Reader reader) {
        var jsonReader = JsonValueReader.newInstance(reader);
        return fromJson(jsonReader);
    }

    @Override
    public void write(char value, Writer writer) {
        var jsonWriter = JsonValueWriter.newInstance(writer);
        toJson(value, jsonWriter);
    }
}
