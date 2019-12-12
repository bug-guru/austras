package guru.bug.austras.web.contentconverter.str;

import guru.bug.austras.convert.converters.StringShortConverter;
import guru.bug.austras.web.MediaType;
import guru.bug.austras.web.contentconverter.Converts;
import guru.bug.austras.web.contentconverter.ShortContentConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@Converts(type = MediaType.TEXT_PLAIN)
public class StringShortContentConverter implements ShortContentConverter {
    private final StringShortConverter shortConverter;

    public StringShortContentConverter(StringShortConverter shortConverter) {
        this.shortConverter = shortConverter;
    }

    @Override
    public short fromString(String value) {
        return shortConverter.fromString(value);
    }

    @Override
    public String toString(short value) {
        return shortConverter.toString(value);
    }

    @Override
    public short read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return shortConverter.fromString(out.toString());
    }

    @Override
    public void write(short value, Writer writer) throws IOException {
        var result = shortConverter.toString(value);
        writer.write(result);
    }
}

