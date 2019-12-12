package guru.bug.austras.web.contentconverter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface FloatContentConverter {
    float fromString(String value);

    String toString(float value);

    float read(Reader reader) throws IOException;

    void write(float value, Writer writer) throws IOException;
}
