package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.JsonConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BooleanJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Boolean> converter = new BooleanJsonConverter();

    @Test
    void toJson() {
        assertEquals("true", toJson(writer -> converter.toJson(Boolean.TRUE, writer)));
    }

    @Test
    void fromJson() {
        assertFalse(converter.fromJson(reader("false")));
    }

}