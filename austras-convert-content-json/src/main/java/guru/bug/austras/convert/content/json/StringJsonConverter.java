package guru.bug.austras.convert.content.json;

import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

@ApplicationJson
public class StringJsonConverter extends AbstractJsonConverter<String> {
    @Override
    public void toJson(String value, JsonValueWriter writer) {
        writer.writeString(value);
    }

    @Override
    public String fromJson(JsonValueReader reader) {
        return reader.readNullableString();
    }

}
