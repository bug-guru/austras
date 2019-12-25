package guru.bug.austras.web.contentconverter.json;

import guru.bug.austras.json.JsonCharacterConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.CharacterContentConverter;
import guru.bug.austras.web.contentconverter.Converts;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.APPLICATION_JSON)
public class JsonCharacterContentConverter implements CharacterContentConverter {
    private final JsonCharacterConverter charConverter;

    public JsonCharacterContentConverter(JsonCharacterConverter charConverter) {
        this.charConverter = charConverter;
    }

    @Override
    public char fromString(String value) {
        return charConverter.fromJson(JsonValueReader.newInstance(new StringReader(value)));
    }

    @Override
    public String toString(char value) {
        var result = new StringWriter(10);
        charConverter.toJson(value, JsonValueWriter.newInstance(result));
        return result.toString();
    }

    @Override
    public char read(Reader reader) {
        return charConverter.fromJson(JsonValueReader.newInstance(reader));
    }

    @Override
    public void write(char value, Writer writer) {
        charConverter.toJson(value, JsonValueWriter.newInstance(writer));
    }
}

