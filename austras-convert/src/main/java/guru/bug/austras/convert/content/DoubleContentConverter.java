package guru.bug.austras.convert.content;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface DoubleContentConverter {
    double fromString(String value);

    String toString(double value);

    double read(Reader reader) throws IOException;

    void write(double value, Writer writer) throws IOException;
}
