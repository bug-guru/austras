package guru.bug.austras.convert.json.writer;

import guru.bug.austras.convert.json.utils.JsonWritingException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonTokenWriterBigDecimalTest extends JsonTokenWriterAbstractTest {

    @Test
    void writeBigDecimal() {
        writer.writeBigDecimal(new BigDecimal("999999999999999999999999999999999999999999999999999999999999999999999999999E-999"));
        assertEquals("9.99999999999999999999999999999999999999999999999999999999999999999999999999E-925", out.toString());
    }

    @Test
    void writeBigDecimalNull() {
        writer.writeBigDecimal(null);
        assertEquals("null", out.toString());
    }

    @Test
    void writeBigDecimalException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeBigDecimal(BigDecimal.ZERO));
    }

}
