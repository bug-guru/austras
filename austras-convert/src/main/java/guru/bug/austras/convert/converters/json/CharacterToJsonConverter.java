package guru.bug.austras.convert.converters.json;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

@Component
public class CharacterToJsonConverter implements JsonConverter<Character> {
    @Override
    public void toJson(Character value, JsonValueWriter writer) {
        writer.write(value);
    }

    @Override
    public Character fromJson(JsonValueReader reader) {
        return reader.readNullableChar();
    }

}
