package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.JsonConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FloatJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Float> converter = new FloatJsonConverter();

    @Test
    void toJson() {
        assertEquals("123.0",
                toJson(writer -> converter.toJson(123F, writer)));
    }

    @Test
    void fromJson() {
        assertEquals(Float.valueOf(123F),
                converter.fromJson(reader("123")));
    }
}