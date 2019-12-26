package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.engine.json.JsonConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BooleanToJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Boolean> converter = new BooleanToJsonConverter();

    @Test
    void toJson() {
        assertEquals("true", toJson(writer -> converter.toJson(Boolean.TRUE, writer)));
    }

    @Test
    void fromJson() {
        assertFalse(converter.fromJson(reader("false")));
    }

}