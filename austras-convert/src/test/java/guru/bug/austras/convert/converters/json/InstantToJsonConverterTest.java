package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.str.InstantToStringConverter;
import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InstantToJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Instant> converter = new InstantToJsonConverter(new InstantToStringConverter());

    @Test
    void toJson() {
        assertEquals("\"2007-12-03T10:15:30Z\"",
                toJson(writer -> converter.toJson(Instant.parse("2007-12-03T10:15:30.00Z"), writer)));
    }

    @Test
    void fromJson() {
        assertEquals(Instant.parse("2007-12-03T10:15:30.00Z"),
                converter.fromJson(reader("\"2007-12-03T10:15:30.00Z\"")));
    }

}