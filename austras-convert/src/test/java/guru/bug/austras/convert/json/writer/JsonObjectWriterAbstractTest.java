package guru.bug.austras.convert.json.writer;

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

    String p(String key, String value) {
        return String.format("%s:%s" , q(key), value);
    }

    String q(String value) {
        return '"' + value + '"';
    }
}
