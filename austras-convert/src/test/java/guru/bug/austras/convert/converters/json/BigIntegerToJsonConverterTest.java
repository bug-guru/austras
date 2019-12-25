package guru.bug.austras.convert.converters.json;

import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BigIntegerToJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<BigInteger> converter = new BigIntegerToJsonConverter();

    @Test
    void toJson() {
        assertEquals("123456",
                toJson(writer -> converter.toJson(BigInteger.valueOf(1234567890), writer)));
    }

    @Test
    void fromJson() {
        assertEquals(BigInteger.valueOf(1234567890),
                converter.fromJson(reader("1234567890")));
    }
}