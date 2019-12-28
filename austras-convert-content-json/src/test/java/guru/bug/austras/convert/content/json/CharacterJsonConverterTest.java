package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.JsonConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharacterJsonConverterTest extends JsonConverterTestBase {
    private JsonConverter<Character> converter = new CharacterJsonConverter();

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