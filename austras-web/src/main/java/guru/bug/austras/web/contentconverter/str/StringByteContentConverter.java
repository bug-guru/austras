package guru.bug.austras.web.contentconverter.str;

import guru.bug.austras.convert.converters.StringBooleanConverter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.BooleanContentConverter;
import guru.bug.austras.web.contentconverter.Converts;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.TEXT_PLAIN)
public class StringByteContentConverter implements BooleanContentConverter {
    private final StringBooleanConverter booleanConverter;

    public StringByteContentConverter(StringBooleanConverter booleanConverter) {
        this.booleanConverter = booleanConverter;
    }

    @Override
    public boolean fromString(String value) {
        return booleanConverter.fromString(value);
    }

    @Override
    public String toString(boolean value) {
        return booleanConverter.toString(value);
    }

    @Override
    public boolean read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return booleanConverter.fromString(out.toString());
    }

    @Override
    public void write(boolean value, Writer writer) throws IOException {
        var result = booleanConverter.toString(value);
        writer.write(result);
    }
}

