package guru.bug.austras.convert.json;

import guru.bug.austras.json.reader.JsonValueReader;
import guru.bug.austras.json.writer.JsonValueWriter;

@ApplicationJson
public class LongJsonConverter extends AbstractJsonConverter<Long> {
    @Override
    public void toJson(Long value, JsonValueWriter writer) {
        writer.writeLong(value);
    }

    @Override
    public Long fromJson(JsonValueReader reader) {
        return reader.readNullableLong();
    }

}
