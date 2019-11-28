package guru.bug.austras.convert.json.writer;

import guru.bug.austras.convert.json.utils.JsonWritingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonTokenWriterIntegerTest extends JsonTokenWriterAbstractTest {

    @Test
    void writeUnboxedIntegerMax() {
        writer.writeInteger(Integer.MAX_VALUE);
        assertEquals(String.valueOf(Integer.MAX_VALUE), out.toString());
    }

    @Test
    void writeUnboxedIntegerMin() {
        writer.writeInteger(Integer.MIN_VALUE);
        assertEquals(String.valueOf(Integer.MIN_VALUE), out.toString());
    }

    @Test
    void writeUnboxedIntegerException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeInteger(0));
    }

    @Test
    void writeBoxedIntegerMax() {
        writer.writeInteger(Integer.valueOf(Integer.MAX_VALUE));
        assertEquals(String.valueOf(Integer.MAX_VALUE), out.toString());
    }

    @Test
    void writeBoxedIntegerMin() {
        writer.writeInteger(Integer.valueOf(Integer.MIN_VALUE));
        assertEquals(String.valueOf(Integer.MIN_VALUE), out.toString());
    }

    @Test
    void writeBoxedIntegerNull() {
        writer.writeInteger(null);
        assertEquals("null", out.toString());
    }

    @Test
    void writeBoxedIntegerException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeInteger(null));
    }

}
