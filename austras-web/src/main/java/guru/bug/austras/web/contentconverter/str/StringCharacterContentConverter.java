package guru.bug.austras.web.contentconverter.str;

import guru.bug.austras.convert.converters.StringCharacterConverter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.CharacterContentConverter;
import guru.bug.austras.web.contentconverter.Converts;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.TEXT_PLAIN)
public class StringCharacterContentConverter implements CharacterContentConverter {
    private final StringCharacterConverter charConverter;

    public StringCharacterContentConverter(StringCharacterConverter charConverter) {
        this.charConverter = charConverter;
    }

    @Override
    public char fromString(String value) {
        return charConverter.fromString(value);
    }

    @Override
    public String toString(char value) {
        return charConverter.toString(value);
    }

    @Override
    public char read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return charConverter.fromString(out.toString());
    }

    @Override
    public void write(char value, Writer writer) throws IOException {
        var result = charConverter.toString(value);
        writer.write(result);
    }
}

