package guru.bug.austras.convert.converters.json;

import guru.bug.austras.core.qualifiers.Default;
import guru.bug.austras.json.JsonConverter;
import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@Default
public class IntegerToJsonConverter implements JsonConverter<Integer> {
    @Override
    public void toJson(Integer value, JsonValueWriter writer) {
        writer.writeInteger(value);
    }

    @Override
    public Integer fromJson(JsonValueReader reader) {
        return reader.readNullableInt();
    }

}
