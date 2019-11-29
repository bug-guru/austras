package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.str.DayOfWeekToStringConverter;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DayOfWeekToJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<DayOfWeek> converter = new DayOfWeekToJsonConverter(new DayOfWeekToStringConverter());

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