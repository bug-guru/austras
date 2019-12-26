package guru.bug.austras.convert.converters.json;


import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

@Default
public class CharacterToJsonConverter implements JsonConverter<Character> {
    @Override
    public void toJson(Character value, JsonValueWriter writer) {
        writer.writeCharacter(value);
    }

    @Override
    public Character fromJson(JsonValueReader reader) {
        return reader.readNullableChar();
    }

}
