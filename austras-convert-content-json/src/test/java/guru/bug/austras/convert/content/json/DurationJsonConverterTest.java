package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.plaintext.DurationPlainTextConverter;
import guru.bug.austras.convert.engine.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DurationJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Duration> converter = new DurationJsonConverter(new DurationPlainTextConverter());

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