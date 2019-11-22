package guru.bug.austras.convert.json.writer;

import java.io.IOException;
import java.io.Writer;

public class ThrowingWriter extends Writer {
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        throw new IOException("Fake write exception");
    }

    @Override
    public void flush() throws IOException {
        throw new IOException("Fake flush exception");
    }

    @Override
    public void close() throws IOException {
        throw new IOException("Fake close exception");
    }
}
