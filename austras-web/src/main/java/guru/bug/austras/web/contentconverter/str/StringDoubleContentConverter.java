package guru.bug.austras.web.contentconverter.str;

import guru.bug.austras.convert.converters.StringDoubleConverter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.Converts;
import guru.bug.austras.web.contentconverter.DoubleContentConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.TEXT_PLAIN)
public class StringDoubleContentConverter implements DoubleContentConverter {
    private final StringDoubleConverter doubleConverter;

    public StringDoubleContentConverter(StringDoubleConverter doubleConverter) {
        this.doubleConverter = doubleConverter;
    }

    @Override
    public double fromString(String value) {
        return doubleConverter.fromString(value);
    }

    @Override
    public String toString(double value) {
        return doubleConverter.toString(value);
    }

    @Override
    public double read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return doubleConverter.fromString(out.toString());
    }

    @Override
    public void write(double value, Writer writer) throws IOException {
        var result = doubleConverter.toString(value);
        writer.write(result);
    }
}

