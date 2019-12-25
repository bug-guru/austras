package guru.bug.austras.convert.converters.json;


import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

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
