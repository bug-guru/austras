package guru.bug.austras.convert.converters.json;


import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

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
