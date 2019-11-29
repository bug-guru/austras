package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharacterToJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Character> converter = new CharacterToJsonConverter();

    @Test
    void toJson() {
        assertEquals("\"A\"",
                toJson(writer -> converter.toJson('A', writer)));
    }

    @Test
    void fromJson() {
        assertEquals('A',
                converter.fromJson(reader("\"A\"")));
    }

}