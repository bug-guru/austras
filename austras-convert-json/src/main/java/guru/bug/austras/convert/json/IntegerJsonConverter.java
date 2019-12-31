package guru.bug.austras.convert.json;

import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@ApplicationJson
public class IntegerJsonConverter extends AbstractJsonConverter<Integer> {
    @Override
    public void toJson(Integer value, JsonValueWriter writer) {
        writer.writeInteger(value);
    }

    @Override
    public Integer fromJson(JsonValueReader reader) {
        return reader.readNullableInt();
    }

}
