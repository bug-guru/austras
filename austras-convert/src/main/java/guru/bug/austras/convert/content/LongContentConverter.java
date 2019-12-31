package guru.bug.austras.convert.content;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface LongContentConverter {
    long fromString(String value);

    String toString(long value);

    long read(Reader reader) throws IOException;

    void write(long value, Writer writer) throws IOException;
}
