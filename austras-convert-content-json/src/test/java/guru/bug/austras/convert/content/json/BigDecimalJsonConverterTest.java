package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BigDecimalJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<BigDecimal> converter = new BigDecimalJsonConverter();

    @Test
    void toJson() {
        assertEquals("123.456",
                toJson(writer -> converter.toJson(BigDecimal.valueOf(123456, 3), writer)));
    }

    @Test
    void fromJson() {
        assertEquals(BigDecimal.valueOf(123456, 3),
                converter.fromJson(reader("123.456")));
    }

}