package guru.bug.austras.convert.content;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface ShortContentConverter {
    short fromString(String value);

    String toString(short value);

    short read(Reader reader) throws IOException;

    void write(short value, Writer writer) throws IOException;
}
