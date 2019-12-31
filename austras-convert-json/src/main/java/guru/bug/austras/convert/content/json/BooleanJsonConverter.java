package guru.bug.austras.convert.content.json;


import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;

@ApplicationJson
public class BooleanJsonConverter extends AbstractJsonConverter<Boolean> {
    @Override
    public void toJson(Boolean value, JsonValueWriter writer) {
        writer.writeBoolean(value);
    }

    @Override
    public Boolean fromJson(JsonValueReader reader) {
        return reader.readNullableBoolean();
    }

}
