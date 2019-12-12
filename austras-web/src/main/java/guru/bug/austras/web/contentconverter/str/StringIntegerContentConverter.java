package guru.bug.austras.web.contentconverter.str;

import guru.bug.austras.convert.converters.StringIntegerConverter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.Converts;
import guru.bug.austras.web.contentconverter.IntegerContentConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.TEXT_PLAIN)
public class StringIntegerContentConverter implements IntegerContentConverter {
    private final StringIntegerConverter integerConverter;

    public StringIntegerContentConverter(StringIntegerConverter integerConverter) {
        this.integerConverter = integerConverter;
    }

    @Override
    public int fromString(String value) {
        return integerConverter.fromString(value);
    }

    @Override
    public String toString(int value) {
        return integerConverter.toString(value);
    }

    @Override
    public int read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return integerConverter.fromString(out.toString());
    }

    @Override
    public void write(int value, Writer writer) throws IOException {
        var result = integerConverter.toString(value);
        writer.write(result);
    }
}

