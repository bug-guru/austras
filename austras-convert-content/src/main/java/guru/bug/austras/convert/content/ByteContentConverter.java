package guru.bug.austras.convert.content;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface ByteContentConverter {
    byte fromString(String value);

    String toString(byte value);

    byte read(Reader reader) throws IOException;

    void write(byte value, Writer writer) throws IOException;
}
