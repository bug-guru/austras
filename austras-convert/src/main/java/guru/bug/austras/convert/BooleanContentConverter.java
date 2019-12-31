package guru.bug.austras.convert;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface BooleanContentConverter {
    boolean fromString(String value);

    String toString(boolean value);

    boolean read(Reader reader) throws IOException;

    void write(boolean value, Writer writer) throws IOException;
}
