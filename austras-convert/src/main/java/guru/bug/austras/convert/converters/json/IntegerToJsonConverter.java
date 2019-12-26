package guru.bug.austras.convert.converters.json;

import guru.bug.austras.convert.engine.json.JsonConverter;
import guru.bug.austras.convert.engine.json.reader.JsonValueReader;
import guru.bug.austras.convert.engine.json.writer.JsonValueWriter;
import guru.bug.austras.core.qualifiers.Default;

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
