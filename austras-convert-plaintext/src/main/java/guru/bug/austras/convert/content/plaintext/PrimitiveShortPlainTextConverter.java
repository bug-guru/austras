package guru.bug.austras.convert.content.plaintext;

import guru.bug.austras.convert.content.ShortContentConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@PlainText
public class PrimitiveShortPlainTextConverter implements ShortContentConverter {
    @Override
    public short fromString(String value) {
        return Short.parseShort(value);
    }

    @Override
    public String toString(short value) {
        return Short.toString(value);
    }

    @Override
    public short read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return fromString(out.toString());
    }

    @Override
    public void write(short value, Writer writer) throws IOException {
        var result = toString(value);
        writer.write(result);
    }

}
