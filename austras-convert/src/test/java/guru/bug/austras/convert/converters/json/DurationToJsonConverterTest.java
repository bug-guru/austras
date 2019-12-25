package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.str.DurationToStringConverter;
import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DurationToJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Duration> converter = new DurationToJsonConverter(new DurationToStringConverter());

    @Test
    void toJson() {
        assertEquals("\"PT1M10S\"",
                toJson(writer -> converter.toJson(Duration.ofSeconds(70), writer)));
    }

    @Test
    void fromJson() {
        assertEquals(Duration.ofHours(1),
                converter.fromJson(reader("\"PT60M\"")));
    }

}