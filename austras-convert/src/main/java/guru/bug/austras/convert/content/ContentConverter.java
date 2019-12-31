package guru.bug.austras.convert.content;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface ContentConverter<T> {
    T fromString(String value);

    String toString(T value);

    T read(Reader reader) throws IOException;

    void write(T value, Writer writer) throws IOException;
}
