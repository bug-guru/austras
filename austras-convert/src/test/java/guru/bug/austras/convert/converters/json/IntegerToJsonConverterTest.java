package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.engine.json.JsonConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerToJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Integer> converter = new IntegerToJsonConverter();

    @Test
    void toJson() {
        assertEquals("123",
                toJson(writer -> converter.toJson(123, writer)));
    }

    @Test
    void fromJson() {
        assertEquals(Integer.valueOf(123),
                converter.fromJson(reader("123")));
    }
}