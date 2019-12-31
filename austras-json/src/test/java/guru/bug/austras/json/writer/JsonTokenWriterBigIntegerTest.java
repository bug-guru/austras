package guru.bug.austras.json.writer;

import guru.bug.austras.json.utils.JsonWritingException;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonTokenWriterBigIntegerTest extends JsonTokenWriterAbstractTest {

    @Test
    void writeBigInteger() {
        writer.writeBigInteger(new BigInteger("999999999999999999999999999999999999999999999999999999999999999999999999999"));
        assertEquals("999999999999999999999999999999999999999999999999999999999999999999999999999", out.toString());
    }

    @Test
    void writeBigIntegerNull() {
        writer.writeBigInteger(null);
        assertEquals("null", out.toString());
    }

    @Test
    void writeBigIntegerException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeBigInteger(BigInteger.ZERO));
    }

}
