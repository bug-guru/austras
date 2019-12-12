package guru.bug.austras.web.contentconverter.str;

import guru.bug.austras.convert.converters.StringLongConverter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.Converts;
import guru.bug.austras.web.contentconverter.LongContentConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.TEXT_PLAIN)
public class StringLongContentConverter implements LongContentConverter {
    private final StringLongConverter longConverter;

    public StringLongContentConverter(StringLongConverter longConverter) {
        this.longConverter = longConverter;
    }

    @Override
    public long fromString(String value) {
        return longConverter.fromString(value);
    }

    @Override
    public String toString(long value) {
        return longConverter.toString(value);
    }

    @Override
    public long read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return longConverter.fromString(out.toString());
    }

    @Override
    public void write(long value, Writer writer) throws IOException {
        var result = longConverter.toString(value);
        writer.write(result);
    }
}

