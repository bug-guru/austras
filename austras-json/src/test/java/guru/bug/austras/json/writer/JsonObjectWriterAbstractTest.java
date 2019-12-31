package guru.bug.austras.json.writer;

import org.junit.jupiter.api.BeforeEach;

import java.io.StringWriter;

abstract class JsonObjectWriterAbstractTest {
    JsonObjectWriter ow;
    StringWriter out;

    @BeforeEach
    void initEach() {
        out = new StringWriter();
        var tokenWriter = new JsonTokenWriter(out);
        var valueWriter = new JsonValueWriterImpl(tokenWriter);
        ow = new JsonObjectWriterImpl(tokenWriter, valueWriter);
    }

    static String p(String key, String value) {
        return String.format("%s:%s", q(key), value);
    }

    static String q(String value) {
        return '"' + value + '"';
    }
}
