package guru.bug.austras.convert.plaintext;

import guru.bug.austras.convert.ContentConverter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public abstract class AbstractPlainTextConverter<T> implements ContentConverter<T> {

    @Override
    public abstract T fromString(String value);

    @Override
    public abstract String toString(T value);

    @Override
    public T read(Reader reader) throws IOException {
        var out = new StringWriter(10);
        reader.transferTo(out);
        return fromString(out.toString());
    }

    @Override
    public void write(T value, Writer writer) throws IOException {
        var result = toString(value);
        writer.write(result);
    }

}
