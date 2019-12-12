package guru.bug.austras.web.contentconverter.str;

import guru.bug.austras.convert.converters.StringFloatConverter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.Converts;
import guru.bug.austras.web.contentconverter.FloatContentConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.TEXT_PLAIN)
public class StringFloatContentConverter implements FloatContentConverter {
    private final StringFloatConverter floatConverter;

    public StringFloatContentConverter(StringFloatConverter floatConverter) {
        this.floatConverter = floatConverter;
    }

    @Override
    public float fromString(String value) {
        return floatConverter.fromString(value);
    }

    @Override
    public String toString(float value) {
        return floatConverter.toString(value);
    }

    @Override
    public float read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return floatConverter.fromString(out.toString());
    }

    @Override
    public void write(float value, Writer writer) throws IOException {
        var result = floatConverter.toString(value);
        writer.write(result);
    }
}

