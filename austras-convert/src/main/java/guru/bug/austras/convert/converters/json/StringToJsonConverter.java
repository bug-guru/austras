package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

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
