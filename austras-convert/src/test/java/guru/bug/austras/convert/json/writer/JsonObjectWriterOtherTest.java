package guru.bug.austras.convert.json.writer;

import guru.bug.austras.convert.json.utils.JsonWritingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonObjectWriterOtherTest extends JsonObjectWriterAbstractTest {

    @Test
    void writeMultipleKeys() {
        ow.writeInteger("key1" , 1);
        ow.writeInteger("key2" , 2);
        assertEquals(p("key1" , "1") + "," + p("key2" , "2"), out.toString());
    }

    @Test
    void writeDuplicateKeys() {
        ow.writeInteger("key1" , 1);
        ow.writeInteger("key2" , 2);
        assertThrows(JsonWritingException.class, () -> ow.writeInteger("key2" , 3));
    }
}