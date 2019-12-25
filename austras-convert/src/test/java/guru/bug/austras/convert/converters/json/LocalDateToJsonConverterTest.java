package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.str.LocalDateToStringConverter;
import guru.bug.austras.json.JsonConverter;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalDateToJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<LocalDate> converter = new LocalDateToJsonConverter(new LocalDateToStringConverter());

    @Test
    void toJson() {
        assertEquals("\"2007-12-03\"",
                toJson(writer -> converter.toJson(LocalDate.parse("2007-12-03"), writer)));
    }

    @Test
    void fromJson() {
        assertEquals(LocalDate.parse("2007-12-03"),
                converter.fromJson(reader("\"2007-12-03\"")));
    }

}