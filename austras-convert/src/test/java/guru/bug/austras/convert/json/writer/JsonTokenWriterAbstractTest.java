package guru.bug.austras.convert.json.writer;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

abstract class JsonTokenWriterAbstractTest {
    StringWriter out;
    JsonTokenWriter writer;
    JsonTokenWriter throwingWriter;

    @BeforeEach
    void init() {
        out = new StringWriter();
        writer = new JsonTokenWriter(out);
        throwingWriter = new JsonTokenWriter(new ThrowingWriter());
    }

    static class ThrowingWriter extends Writer {
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
}
