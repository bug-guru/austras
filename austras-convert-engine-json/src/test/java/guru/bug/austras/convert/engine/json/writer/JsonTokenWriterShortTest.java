package guru.bug.austras.convert.engine.json.writer;

import guru.bug.austras.convert.engine.json.utils.JsonWritingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonTokenWriterShortTest extends JsonTokenWriterAbstractTest {

    @Test
    void writeUnboxedShortMax() {
        writer.writeShort(Short.MAX_VALUE);
        assertEquals(String.valueOf(Short.MAX_VALUE), out.toString());
    }

    @Test
    void writeUnboxedShortMin() {
        writer.writeShort(Short.MIN_VALUE);
        assertEquals(String.valueOf(Short.MIN_VALUE), out.toString());
    }


    @Test
    void writeUnboxedShortException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeShort((short) 0));
    }

    @Test
    void writeBoxedShortMax() {
        writer.writeShort(Short.valueOf(Short.MAX_VALUE));
        assertEquals(String.valueOf(Short.MAX_VALUE), out.toString());
    }

    @Test
    void writeBoxedShortMin() {
        writer.writeShort(Short.valueOf(Short.MIN_VALUE));
        assertEquals(String.valueOf(Short.MIN_VALUE), out.toString());
    }

    @Test
    void writeBoxedShortNull() {
        writer.writeShort(null);
        assertEquals("null", out.toString());
    }

    @Test
    void writeBoxedShortException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeShort(null));
    }

}