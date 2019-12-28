package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.JsonConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ByteJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Byte> converter = new ByteJsonConverter();

    @Test
    void toJson() {
        assertEquals("123",
                toJson(writer -> converter.toJson((byte) 123, writer)));
    }

    @Test
    void fromJson() {
        assertEquals(Byte.valueOf((byte) 123),
                converter.fromJson(reader("123")));
    }

}