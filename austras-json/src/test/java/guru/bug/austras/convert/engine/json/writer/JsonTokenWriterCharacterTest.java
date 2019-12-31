package guru.bug.austras.convert.engine.json.writer;

import guru.bug.austras.convert.engine.json.utils.JsonWritingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonTokenWriterCharacterTest extends JsonTokenWriterAbstractTest {

    @Test
    void writeUnboxedCharacterMin() {
        writer.writeCharacter(Character.MIN_VALUE);
        assertEquals("\"\u0000\"", out.toString());
    }

    @Test
    void writeUnboxedCharacterMax() {
        writer.writeCharacter(Character.MAX_VALUE);
        assertEquals("\"\uFFFF\"", out.toString());
    }

    @Test
    void writeUnboxedCharacterException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeCharacter('x'));
    }

    @Test
    void writeBoxedCharacterMin() {
        writer.writeCharacter(Character.valueOf(Character.MIN_VALUE));
        assertEquals("\"\u0000\"", out.toString());
    }

    @Test
    void writeBoxedCharacterMax() {
        writer.writeCharacter(Character.valueOf(Character.MAX_VALUE));
        assertEquals("\"\uFFFF\"", out.toString());
    }

    @Test
    void writeBoxedCharacterException() {
        assertThrows(JsonWritingException.class, () -> throwingWriter.writeCharacter(null));
    }

}
