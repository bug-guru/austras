package guru.bug.austras.convert.json.writer;

import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonValueWriterFactoryTest {

    @Test
    void newInstance() {
        var out = new StringWriter();
        var vw = JsonValueWriter.newInstance(out);
        vw.writeNull();
        assertEquals("null" , out.toString());
    }
}