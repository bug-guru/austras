package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.converters.str.LocalDateTimeToStringConverter;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalDateTimeToJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<LocalDateTime> converter = new LocalDateTimeToJsonConverter(new LocalDateTimeToStringConverter());

    @Test
    void toJson() {
        assertEquals("\"2007-12-03T10:15:30\"",
                toJson(writer -> converter.toJson(LocalDateTime.parse("2007-12-03T10:15:30"), writer)));
    }

    @Test
    void fromJson() {
        assertEquals(LocalDateTime.parse("2007-12-03T10:15:30"),
                converter.fromJson(reader("\"2007-12-03T10:15:30\"")));
    }

}