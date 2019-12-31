package guru.bug.austras.convert;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface IntegerContentConverter {
    int fromString(String value);

    String toString(int value);

    int read(Reader reader) throws IOException;

    void write(int value, Writer writer) throws IOException;
}
