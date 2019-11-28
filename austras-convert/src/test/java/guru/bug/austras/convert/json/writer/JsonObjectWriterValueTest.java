package guru.bug.austras.convert.json.writer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonObjectWriterValueTest extends JsonObjectWriterAbstractTest {

    @Test
    void writeValue() {
        ow.writeValue("key", new Object(), (v, o) -> o.writeString("TEST"));
        assertEquals(p("key", q("TEST")), out.toString());
    }

    @Test
    void writeValueNull() {
        ow.writeValue("key", null, (v, o) -> o.writeString("TEST"));
        assertEquals(p("key", q("TEST")), out.toString());
    }

    @Test
    void writeValueArrayArray() {
        ow.writeValueArray("key", new String[]{"5", "100", null},
                (v, o) -> o.writeInteger(v == null ? -1 : Integer.parseInt(v)));
        assertEquals(p("key", "[5,100,-1]"), out.toString());
    }

    @Test
    void writeValueArrayArrayEmpty() {
        ow.writeValueArray("key", new String[]{},
                (v, o) -> o.writeInteger(v == null ? -1 : Integer.parseInt(v)));
        assertEquals(p("key", "[]"), out.toString());
    }

    @Test
    void writeValueArrayArrayNull() {
        ow.writeValueArray("key", (String[]) null,
                (v, o) -> o.writeInteger(v == null ? -1 : Integer.parseInt(v)));
        assertEquals(p("key", "null"), out.toString());
    }

    @Test
    void writeValueArrayCollection() {
        ow.writeValueArray("key", Arrays.asList("32", "44", null),
                (v, o) -> o.writeInteger(v == null ? -1 : Integer.parseInt(v)));
        assertEquals(p("key", "[32,44,-1]"), out.toString());
    }

    @Test
    void writeValueArrayCollectionEmpty() {
        ow.writeValueArray("key", List.of(),
                (String v, JsonValueWriter o) -> o.writeInteger(v == null ? -1 : Integer.parseInt(v)));
        assertEquals(p("key", "[]"), out.toString());
    }

    @Test
    void writeValueArrayCollectionNull() {
        ow.writeValueArray("key", (Collection<String>) null,
                (v, o) -> o.writeInteger(v == null ? -1 : Integer.parseInt(v)));
        assertEquals(p("key", "null"), out.toString());
    }

}
