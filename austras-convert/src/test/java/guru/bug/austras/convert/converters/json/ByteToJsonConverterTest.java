package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ByteToJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Byte> converter = new ByteToJsonConverter();

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