package guru.bug.austras.convert.converters.json;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@Default
public class StringToJsonConverter implements JsonConverter<String> {
    @Override
    public void toJson(String value, JsonValueWriter writer) {
        writer.writeString(value);
    }

    @Override
    public String fromJson(JsonValueReader reader) {
        return reader.readNullableString();
    }

}
