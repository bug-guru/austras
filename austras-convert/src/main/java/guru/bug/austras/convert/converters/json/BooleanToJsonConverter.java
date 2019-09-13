package guru.bug.austras.convert.converters.json;


import guru.bug.austras.convert.converters.JsonConverter;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;
import guru.bug.austras.core.Component;

@Component
public class BooleanToJsonConverter implements JsonConverter<Boolean> {
    @Override
    public void toJson(Boolean value, JsonValueWriter writer) {
        writer.write(value);
    }

    @Override
    public Boolean fromJson(JsonValueReader reader) {
        return reader.readNullableBoolean();
    }

}
