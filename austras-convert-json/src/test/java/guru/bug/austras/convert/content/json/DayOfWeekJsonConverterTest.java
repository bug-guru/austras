package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.content.plaintext.DayOfWeekPlainTextConverter;
import guru.bug.austras.convert.engine.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DayOfWeekJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<DayOfWeek> converter = new DayOfWeekJsonConverter(new DayOfWeekPlainTextConverter());

    @Test
    void toJson() {
        assertEquals("\"FRIDAY\"",
                toJson(writer -> converter.toJson(DayOfWeek.FRIDAY, writer)));
    }

    @Test
    void fromJson() {
        assertEquals(DayOfWeek.MONDAY,
                converter.fromJson(reader("\"MONDAY\"")));
    }

}