package guru.bug.austras.convert.json.writer;

import guru.bug.austras.convert.json.utils.JsonWritingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonTokenWriterDoubleTest extends JsonTokenWriterAbstractTest {

    @Test
    void writeUnboxedDoubleMax() {
        writer.writeDouble(Double.MAX_VALUE);
        assertEquals(String.valueOf(Double.MAX_VALUE), out.toString());
    }

    @Test
    void writeUnboxedDoubleMin() {
        writer.writeDouble(Double.MIN_VALUE);
        assertEquals(String.valueOf(Double.MIN_VALUE), out.toString());
    }

    @Test
    void writeUnboxedDoubleNaN() {
        writer.writeDouble(Double.NaN);
        assertEquals("null", out.toString());
    }

    @Test
    void writeUnboxedDoublePositiveInfinity() {
        writer.writeDouble(Double.POSITIVE_INFINITY);
        assertEquals("null", out.toString());
    }

    @Test
    void writeUnboxedDoubleNegativeInfinity() {
        writer.writeDouble(Double.NEGATIVE_INFINITY);
        assertEquals("null", out.toString());
    }

    @Test
    void writeUnboxedDoubleException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeDouble(0D));
    }

    @Test
    void writeBoxedDoubleMax() {
        writer.writeDouble(Double.valueOf(Double.MAX_VALUE));
        assertEquals(String.valueOf(Double.MAX_VALUE), out.toString());
    }

    @Test
    void writeBoxedDoubleMin() {
        writer.writeDouble(Double.valueOf(Double.MIN_VALUE));
        assertEquals(String.valueOf(Double.MIN_VALUE), out.toString());
    }

    @Test
    void writeBoxedDoubleNaN() {
        writer.writeDouble(Double.valueOf(Double.NaN));
        assertEquals("null", out.toString());
    }

    @Test
    void writeBoxedDoublePositiveInfinity() {
        writer.writeDouble(Double.valueOf(Double.POSITIVE_INFINITY));
        assertEquals("null", out.toString());
    }

    @Test
    void writeBoxedDoubleNegativeInfinity() {
        writer.writeDouble(Double.valueOf(Double.NEGATIVE_INFINITY));
        assertEquals("null", out.toString());
    }

    @Test
    void writeBoxedDoubleException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeDouble(null));
    }

}
