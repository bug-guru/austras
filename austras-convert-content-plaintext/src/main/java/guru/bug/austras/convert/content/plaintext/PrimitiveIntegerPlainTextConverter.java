package guru.bug.austras.convert.content.plaintext;

import guru.bug.austras.convert.content.IntegerContentConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@PlainText
public class PrimitiveIntegerPlainTextConverter implements IntegerContentConverter {
    @Override
    public int fromString(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public String toString(int value) {
        return Integer.toString(value);
    }

    @Override
    public int read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return fromString(out.toString());
    }

    @Override
    public void write(int value, Writer writer) throws IOException {
        var result = toString(value);
        writer.write(result);
    }

}