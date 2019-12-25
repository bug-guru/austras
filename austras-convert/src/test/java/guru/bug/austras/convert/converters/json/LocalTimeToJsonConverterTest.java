package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.str.LocalTimeToStringConverter;
import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalTimeToJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<LocalTime> converter = new LocalTimeToJsonConverter(new LocalTimeToStringConverter());

    @Test
    void toJson() {
        assertEquals("\"10:15:30\"",
                toJson(writer -> converter.toJson(LocalTime.parse("10:15:30.00"), writer)));
    }

    @Test
    void fromJson() {
        assertEquals(LocalTime.parse("10:15:30.00"),
                converter.fromJson(reader("\"10:15:30.00\"")));
    }

}