package guru.bug.austras.convert.plaintext;

import guru.bug.austras.convert.BooleanContentConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

@PlainText
public class PrimitiveBooleanPlainTextConverter implements BooleanContentConverter {
    @Override
    public boolean fromString(String value) {
        return Boolean.parseBoolean(value);
    }

    @Override
    public String toString(boolean obj) {
        return String.valueOf(obj);
    }

    @Override
    public boolean read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return fromString(out.toString());
    }

    @Override
    public void write(boolean value, Writer writer) throws IOException {
        var result = toString(value);
        writer.write(result);
    }
}
