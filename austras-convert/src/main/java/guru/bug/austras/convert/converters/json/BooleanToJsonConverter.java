package guru.bug.austras.convert.converters.json;


import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@Default
public class BooleanToJsonConverter implements JsonConverter<Boolean> {
    @Override
    public void toJson(Boolean value, JsonValueWriter writer) {
        writer.writeBoolean(value);
    }

    @Override
    public Boolean fromJson(JsonValueReader reader) {
        return reader.readNullableBoolean();
    }

}
