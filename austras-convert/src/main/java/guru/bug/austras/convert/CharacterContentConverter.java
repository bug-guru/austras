package guru.bug.austras.convert;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface CharacterContentConverter {
    char fromString(String value);

    String toString(char value);

    char read(Reader reader) throws IOException;

    void write(char value, Writer writer) throws IOException;
}
