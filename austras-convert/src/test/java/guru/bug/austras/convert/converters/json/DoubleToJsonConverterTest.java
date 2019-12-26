package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.engine.json.JsonConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DoubleToJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Double> converter = new DoubleToJsonConverter();

    @Test
    void toJson() {
        assertEquals("123.0",
                toJson(writer -> converter.toJson(123.0, writer)));
    }

    @Test
    void fromJson() {
        assertEquals(123.0,
                converter.fromJson(reader("123.0")));
    }
}