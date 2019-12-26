package guru.bug.austras.convert.content.json;


import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

@ApplicationJson
public class CharacterJsonConverter extends AbstractJsonConverter<Character> {
    @Override
    public void toJson(Character value, JsonValueWriter writer) {
        writer.writeCharacter(value);
    }

    @Override
    public Character fromJson(JsonValueReader reader) {
        return reader.readNullableChar();
    }

}
